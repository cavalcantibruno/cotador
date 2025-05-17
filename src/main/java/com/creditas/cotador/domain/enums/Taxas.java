package com.creditas.cotador.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Taxas {

    TAXA_FIXA(0.0),
    TAXA_SELIC(1.5),
    TAXA_CDI(10.5);

    private Double taxaAnual;
}
