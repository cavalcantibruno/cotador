package com.creditas.cotador.domain.service.impl;

import com.creditas.cotador.api.v1.dto.SimulacaoRequestDto;
import com.creditas.cotador.api.v1.dto.SimulacaoResponseDto;
import com.creditas.cotador.domain.service.CotacaoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;


@Slf4j
@AllArgsConstructor
@Component
public class CotacaoServiceImpl implements CotacaoService {

    private final CalcularServiceImpl calcularService;
    private final EnviarEmailServiceImpl enviarEmailService;

    @Override
    public SimulacaoResponseDto simulacaoDeCreditoSync(SimulacaoRequestDto request) {
        int prazoEmMeses = request.getPrazo();
        BigDecimal valorSolicitado = BigDecimal.valueOf(request.getValorCredito());

        double taxaJurosAnual = calcularService.calcularTaxaDeJurosAnual(request.getDataNascimento());
        BigDecimal valorParcelaMensal = calcularService.calcularValorParcelaMensal(valorSolicitado, taxaJurosAnual, prazoEmMeses);

        BigDecimal valorTotalPago = valorParcelaMensal.multiply(BigDecimal.valueOf(prazoEmMeses)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal jurosTotal = valorTotalPago.subtract(valorSolicitado).setScale(2, RoundingMode.HALF_UP);

        return SimulacaoResponseDto.builder()
                .idCotacao(UUID.randomUUID())
                .valorSolicitado(valorSolicitado.setScale(2, RoundingMode.HALF_UP))
                .prazo(prazoEmMeses)
                .taxaJuros(taxaJurosAnual)
                .valorParcelaMensal(valorParcelaMensal)
                .valorTotalComJuros(valorTotalPago)
                .jurosTotal(jurosTotal)
                .build();
    }

    /**
     * Esse metodo poderia enviar a lista de cotações para uma fila ou mensageria.
     * Após enviar as cotações um outro metodo iria "escutar" e processar essa lista
     * Para cada retorno de calculo ele faria o envio por email.
     * Para simplificar e abstrair a implementacao, faremos o processamos de forma Asincrona usando @Async,
     * mas o envio do email sera feito um a um atraves de um forEach.
     *
     * @param request
     */
    @Override
    @Async
    public void simulacaoDeCreditoAsync(List<SimulacaoRequestDto> request) {
        request.forEach(req -> {
            SimulacaoResponseDto simulacaoResponseDto = this.simulacaoDeCreditoSync(req);
            enviarEmailService.enviarCotacaoPorEmail(simulacaoResponseDto, req);
        });
    }
}
