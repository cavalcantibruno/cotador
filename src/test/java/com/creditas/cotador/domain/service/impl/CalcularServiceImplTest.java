package com.creditas.cotador.domain.service.impl;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalcularServiceImplTest {

    @Test
    void calcularTaxaDeJurosAnualPorIdade() {
        CalcularServiceImpl service = new CalcularServiceImpl();

        // Idade <= 25
        assertEquals(5.0, service.calcularTaxaDeJurosAnualPorIdade("2000-01-01"));

        // Idade == 25 (caso limite)
        assertEquals(5.0, service.calcularTaxaDeJurosAnualPorIdade(LocalDate.now().minusYears(25).toString()));

        // Idade entre 26 e 40
        assertEquals(3.0, service.calcularTaxaDeJurosAnualPorIdade("1985-01-01"));

        // Idade == 40 (caso limite)
        assertEquals(3.0, service.calcularTaxaDeJurosAnualPorIdade(LocalDate.now().minusYears(40).toString()));

        // Idade entre 41 e 60
        assertEquals(2.0, service.calcularTaxaDeJurosAnualPorIdade("1965-01-01"));

        // Idade == 60 (caso limite)
        assertEquals(2.0, service.calcularTaxaDeJurosAnualPorIdade(LocalDate.now().minusYears(60).toString()));

        // Idade > 60
        assertEquals(4.0, service.calcularTaxaDeJurosAnualPorIdade("1950-01-01"));
    }

    @Test
    void calcularIdade() {
        CalcularServiceImpl service = new CalcularServiceImpl();

        // Caso de teste: Nascido hoje
        assertEquals(0, service.calcularIdade(LocalDate.now().toString()));

        // Caso de teste: Nascido exatamente há 25 anos
        assertEquals(25, service.calcularIdade(LocalDate.now().minusYears(25).toString()));

        // Caso de teste: Nascido há 40 anos
        assertEquals(40, service.calcularIdade(LocalDate.now().minusYears(40).toString()));

        // Caso de teste: Nascido há 60 anos
        assertEquals(60, service.calcularIdade(LocalDate.now().minusYears(60).toString()));
    }

    @Test
    void calcularComTaxaFixa() {
        CalcularServiceImpl service = new CalcularServiceImpl();

        // Caso de teste: taxa anual > 0
        BigDecimal valorSolicitado1 = BigDecimal.valueOf(10000);
        double taxaAnual1 = 5.0;
        int prazo1 = 12;
        BigDecimal esperado1 = BigDecimal.valueOf(856.07); // Expected output from calculation
        assertEquals(esperado1, service.calcularComTaxaFixa(valorSolicitado1, taxaAnual1, prazo1));

        // Caso de teste: taxa anual = 0
        BigDecimal valorSolicitado2 = BigDecimal.valueOf(12000);
        double taxaAnual2 = 0.0;
        int prazo2 = 12;
        BigDecimal esperado2 = new BigDecimal("1000.00"); // 12000 / 12
        assertEquals(esperado2, service.calcularComTaxaFixa(valorSolicitado2, taxaAnual2, prazo2));
    }

    @Test
    void calcularComTaxaVariavel() {
        CalcularServiceImpl service = new CalcularServiceImpl();

        // Caso de teste: taxa anual > 0
        BigDecimal saldoDevedor1 = BigDecimal.valueOf(50000);
        double taxaAnual1 = 7.0;
        int prazo1 = 24;
        BigDecimal esperado1 = BigDecimal.valueOf(2238.63); // Expected output from calculation
        assertEquals(esperado1, service.calcularComTaxaVariavel(saldoDevedor1, taxaAnual1, prazo1));

        // Caso de teste: taxa anual = 0
        BigDecimal saldoDevedor2 = BigDecimal.valueOf(60000);
        double taxaAnual2 = 0.0;
        int prazo2 = 36;
        BigDecimal esperado2 = BigDecimal.valueOf(1666.67); // 60000 / 36
        assertEquals(esperado2, service.calcularComTaxaVariavel(saldoDevedor2, taxaAnual2, prazo2));
    }
}