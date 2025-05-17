package com.creditas.cotador.api.v1.controller;

import com.creditas.cotador.api.v1.dto.CotacaoResponseDto;
import com.creditas.cotador.api.v1.dto.SimulacaoRequestDto;
import com.creditas.cotador.api.v1.dto.SimulacaoResponseDto;
import com.creditas.cotador.domain.enums.Taxas;
import com.creditas.cotador.domain.service.CotacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CotacaoControllerTest {

    @Mock
    private CotacaoService cotacaoService;

    @InjectMocks
    private CotacaoController cotacaoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void simulacaoDeCreditoSync() {

        SimulacaoRequestDto request = new SimulacaoRequestDto(10000.00, "2000-06-21", 24, "jaba@gmail.com", "Jaba");
        Taxas taxa = Taxas.TAXA_FIXA;
        SimulacaoResponseDto expectedResponse = SimulacaoResponseDto.builder()
                .idCotacao(UUID.randomUUID())
                .prazo(24)
                .taxaJuros(3.0)
                .jurosTotal(BigDecimal.valueOf(1500.00))
                .valorSolicitado(BigDecimal.valueOf(10000.00))
                .valorParcelaMensal(BigDecimal.valueOf(500.00))
                .valorTotalComJuros(BigDecimal.valueOf(11500.00))
                .build();

        when(cotacaoService.simulacaoDeCreditoSync(request, taxa)).thenReturn(expectedResponse);

        SimulacaoResponseDto response = cotacaoController.simulacaoDeCreditoSync(request, taxa);

        assertNotNull(response);
        assertEquals(expectedResponse.getPrazo(), response.getPrazo());
        assertEquals(expectedResponse.getTaxaJuros(), response.getTaxaJuros());
        verify(cotacaoService).simulacaoDeCreditoSync(request, taxa);
    }

    @Test
    void simulacaoDeCreditoSync_InvalidRequest() {

        SimulacaoRequestDto request = new SimulacaoRequestDto(null, null, null, null, null);
        Taxas taxa = Taxas.TAXA_FIXA;
        when(cotacaoService.simulacaoDeCreditoSync(request, taxa)).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> cotacaoController.simulacaoDeCreditoSync(request, taxa));
        verify(cotacaoService).simulacaoDeCreditoSync(request, taxa);
    }

    @Test
    void simulacaoDeCreditoSync_DefaultTaxa() {
        SimulacaoRequestDto request = new SimulacaoRequestDto(10000.00, "1990-05-08", 48, "jp89@gmail.com", "Joao");
        SimulacaoResponseDto expectedResponse = SimulacaoResponseDto.builder()
                .idCotacao(UUID.randomUUID())
                .prazo(48)
                .taxaJuros(3.0)
                .jurosTotal(BigDecimal.valueOf(1500.00))
                .valorSolicitado(BigDecimal.valueOf(10000.00))
                .valorParcelaMensal(BigDecimal.valueOf(500.00))
                .valorTotalComJuros(BigDecimal.valueOf(11500.00))
                .build();

        when(cotacaoService.simulacaoDeCreditoSync(request, Taxas.TAXA_FIXA)).thenReturn(expectedResponse);

        SimulacaoResponseDto response = cotacaoController.simulacaoDeCreditoSync(request, Taxas.TAXA_FIXA);

        assertNotNull(response);
        assertEquals(expectedResponse.getPrazo(), response.getPrazo());
        assertEquals(expectedResponse.getTaxaJuros(), response.getTaxaJuros());
        verify(cotacaoService).simulacaoDeCreditoSync(request, Taxas.TAXA_FIXA);

    }

    @Test
    void simulacaoDeCreditoAsync_Success () {

        List<SimulacaoRequestDto> request = List.of(
                new SimulacaoRequestDto(10000.00, "1994-01-15", 24, "leia@gmail.com", "Leia Organa"),
                new SimulacaoRequestDto(20000.00, "1991-02-04", 36, "anakin@gmail.com", "Anakin S.")
        );
        CotacaoResponseDto expectedResponse = CotacaoResponseDto.builder()
                .status("SUCESSO")
                .mensagem("Solicitação realizada com sucesso")
                .build();


        CotacaoResponseDto response = cotacaoController.simulacaoDeCreditoAsync(request);

        assertNotNull(response);
        assertEquals(expectedResponse.getStatus(), response.getStatus());
        assertEquals(expectedResponse.getMensagem(), response.getMensagem());
        verify(cotacaoService).simulacaoDeCreditoAsync(request);
    }

    @Test
    void simulacaoDeCreditoAsync_EmptyRequest () {
        List<SimulacaoRequestDto> request = List.of();
        CotacaoResponseDto expectedResponse = CotacaoResponseDto.builder()
                .status("SUCESSO")
                .mensagem("Solicitação realizada com sucesso")
                .build();

        CotacaoResponseDto response = cotacaoController.simulacaoDeCreditoAsync(request);

        assertNotNull(response);
        assertEquals(expectedResponse.getStatus(), response.getStatus());
        assertEquals(expectedResponse.getMensagem(), response.getMensagem());
        verify(cotacaoService).simulacaoDeCreditoAsync(request);
    }
}