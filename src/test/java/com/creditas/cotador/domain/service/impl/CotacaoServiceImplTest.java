package com.creditas.cotador.domain.service.impl;

import com.creditas.cotador.api.v1.dto.SimulacaoRequestDto;
import com.creditas.cotador.api.v1.dto.SimulacaoResponseDto;
import com.creditas.cotador.domain.enums.Taxas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CotacaoServiceImplTest {

    @Mock
    private CalcularServiceImpl calcularService;

    @Mock
    private EnviarEmailServiceImpl enviarEmailService;

    private CotacaoServiceImpl cotacaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cotacaoService = new CotacaoServiceImpl(calcularService, enviarEmailService);
    }

    @Test
    void simulacaoDeCreditoSync_TaxaFixa() {
        SimulacaoRequestDto request = new SimulacaoRequestDto(10000.0, "1990-01-01", 24, "han_solo@icloud.com", "Obi-Wan");
        when(calcularService.calcularTaxaDeJurosAnualPorIdade("1990-01-01")).thenReturn(3.0);
        when(calcularService.calcularComTaxaFixa(BigDecimal.valueOf(10000.0), 3.0, 24))
                .thenReturn(BigDecimal.valueOf(429.43));

        SimulacaoResponseDto response = cotacaoService.simulacaoDeCreditoSync(request, Taxas.TAXA_FIXA);

        assertEquals(BigDecimal.valueOf(10000.0).setScale(2), response.getValorSolicitado());
        assertEquals(24, response.getPrazo());
        assertEquals(3.0, response.getTaxaJuros());
        assertEquals(BigDecimal.valueOf(429.43).setScale(2), response.getValorParcelaMensal());
        assertEquals(BigDecimal.valueOf(10306.32).setScale(2), response.getValorTotalComJuros());
        assertEquals(BigDecimal.valueOf(306.32).setScale(2), response.getJurosTotal());
    }

    @Test
    void simulacaoDeCreditoSync_TaxaVariavel() {
        SimulacaoRequestDto request = new SimulacaoRequestDto(10000.0, "1990-01-01", 24, "han_solo@icloud.com", "Obi-Wan");
        when(calcularService.calcularComTaxaVariavel(BigDecimal.valueOf(10000.0), 1.5, 24))
                .thenReturn(BigDecimal.valueOf(419.17));

        SimulacaoResponseDto response = cotacaoService.simulacaoDeCreditoSync(request, Taxas.TAXA_SELIC);

        assertEquals(BigDecimal.valueOf(10000.0).setScale(2), response.getValorSolicitado());
        assertEquals(24, response.getPrazo());
        assertEquals(0.0, response.getTaxaJuros());
        assertEquals(BigDecimal.valueOf(419.17).setScale(2), response.getValorParcelaMensal());
        assertEquals(BigDecimal.valueOf(10060.08).setScale(2), response.getValorTotalComJuros());
        assertEquals(BigDecimal.valueOf(60.08).setScale(2), response.getJurosTotal());
    }

    @Test
    void simulacaoDeCreditoAsync_Success() {
        // Arrange
        SimulacaoRequestDto request1 = new SimulacaoRequestDto(5000.0, "1985-09-20", 18, "han_solo@icloud.com", "Han");
        SimulacaoRequestDto request2 = new SimulacaoRequestDto(8000.0, "1992-03-15", 24, "padmeamidala84@outlook.com", "Padme");
        List<SimulacaoRequestDto> requests = List.of(request1, request2);

        when(calcularService.calcularTaxaDeJurosAnualPorIdade("1985-09-20")).thenReturn(3.0);
        when(calcularService.calcularComTaxaFixa(BigDecimal.valueOf(5000.0), 3.0, 18))
                .thenReturn(BigDecimal.valueOf(301.33));

        when(calcularService.calcularTaxaDeJurosAnualPorIdade("1992-03-15")).thenReturn(4.5);
        when(calcularService.calcularComTaxaFixa(BigDecimal.valueOf(8000.0), 4.5, 24))
                .thenReturn(BigDecimal.valueOf(364.05));

        cotacaoService.simulacaoDeCreditoAsync(requests);

        ArgumentCaptor<SimulacaoResponseDto> responseCaptor = ArgumentCaptor.forClass(SimulacaoResponseDto.class);
        ArgumentCaptor<SimulacaoRequestDto> requestCaptor = ArgumentCaptor.forClass(SimulacaoRequestDto.class);

        verify(enviarEmailService, times(2)).enviarCotacaoPorEmail(responseCaptor.capture(), requestCaptor.capture());

        assertEquals(BigDecimal.valueOf(5000).setScale(2), responseCaptor.getAllValues().get(0).getValorSolicitado());
        assertEquals(18, responseCaptor.getAllValues().get(0).getPrazo());
        assertEquals(3.0, responseCaptor.getAllValues().get(0).getTaxaJuros());
        assertEquals(BigDecimal.valueOf(301.33).setScale(2), responseCaptor.getAllValues().get(0).getValorParcelaMensal());

        // Verify the content of the second call
        assertEquals(BigDecimal.valueOf(8000).setScale(2), responseCaptor.getAllValues().get(1).getValorSolicitado());
        assertEquals(24, responseCaptor.getAllValues().get(1).getPrazo());
        assertEquals(4.5, responseCaptor.getAllValues().get(1).getTaxaJuros());
        assertEquals(BigDecimal.valueOf(364.05).setScale(2), responseCaptor.getAllValues().get(1).getValorParcelaMensal());
    }
}