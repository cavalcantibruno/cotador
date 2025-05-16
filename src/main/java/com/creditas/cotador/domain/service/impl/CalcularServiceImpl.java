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
    public double calcularTaxaDeJurosAnual(String dataNascimento) {
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
    public BigDecimal calcularValorParcelaMensal(BigDecimal valorPresente, double taxaAnual, int meses) {
        if (taxaAnual <= 0.0) {
            return valorPresente.divide(BigDecimal.valueOf(meses), 2, RoundingMode.HALF_UP);
        }

        BigDecimal taxaMensal = BigDecimal.valueOf(taxaAnual).divide(CEM, 10, RoundingMode.HALF_UP)
                .divide(DOZE, 10, RoundingMode.HALF_UP);

        BigDecimal fator = BigDecimal.ONE.add(taxaMensal).pow(meses);
        BigDecimal parcela = valorPresente.multiply(taxaMensal).multiply(fator)
                .divide(fator.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);

        return parcela;
    }
}
