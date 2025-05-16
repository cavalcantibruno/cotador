package com.creditas.cotador.domain.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface CalcularService {
    double calcularTaxaDeJurosAnual(String dataNascimento);
    BigDecimal calcularValorParcelaMensal(BigDecimal valorPresente, double taxaAnual, int meses);
}
