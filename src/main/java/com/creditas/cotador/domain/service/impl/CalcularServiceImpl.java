package com.creditas.cotador.domain.service.impl;

import com.creditas.cotador.domain.service.CalcularService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

@AllArgsConstructor
@Component
public class CalcularServiceImpl implements CalcularService {

    private static final BigDecimal CEM = BigDecimal.valueOf(100);
    private static final BigDecimal DOZE = BigDecimal.valueOf(12);

    @Override
    public double calcularTaxaDeJurosAnualPorIdade(String dataNascimento) {
        int idade = calcularIdade(dataNascimento);

        if (idade <= 25) return 5.0;
        if (idade <= 40) return 3.0;
        if (idade <= 60) return 2.0;
        return 4.0;
    }

    public int calcularIdade(String dataNascimento) {
        LocalDate nascimento = LocalDate.parse(dataNascimento);
        return Period.between(nascimento, LocalDate.now()).getYears();
    }

    @Override
    public BigDecimal calcularComTaxaFixa(BigDecimal valorSolicitado, double taxaAnual, int prazo) {
        if (taxaAnual <= 0.0) {
            return valorSolicitado.divide(BigDecimal.valueOf(prazo), 2, RoundingMode.HALF_UP);
        }

        return calcularParcelaMensal(valorSolicitado, taxaAnual, prazo);
    }

    @Override
    public BigDecimal calcularComTaxaVariavel(BigDecimal saldoDevedor, Double taxaAnual, int prazo) {

        if (taxaAnual <= 0.0) {
            return saldoDevedor.divide(BigDecimal.valueOf(prazo), 2, RoundingMode.HALF_UP);
        }

        return calcularParcelaMensal(saldoDevedor, taxaAnual, prazo);
    }

    private static BigDecimal calcularParcelaMensal(BigDecimal valor, Double taxaAnual, int prazo) {
        BigDecimal taxaMensal = BigDecimal.valueOf(taxaAnual)
                .divide(CEM, 10, RoundingMode.HALF_UP)
                .divide(DOZE, 10, RoundingMode.HALF_UP);

        BigDecimal fator = BigDecimal.ONE.add(taxaMensal).pow(prazo);

        return valor.multiply(taxaMensal).multiply(fator)
                .divide(fator.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
    }
}
