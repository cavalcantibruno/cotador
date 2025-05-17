package com.creditas.cotador.domain.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface CalcularService {
    double calcularTaxaDeJurosAnualPorIdade(String dataNascimento);
    BigDecimal calcularComTaxaFixa(BigDecimal valorPresente, double taxaAnual, int meses);
    BigDecimal calcularComTaxaVariavel(BigDecimal valorSolicitado, Double taxaAnual, int prazoEmMeses);
}
