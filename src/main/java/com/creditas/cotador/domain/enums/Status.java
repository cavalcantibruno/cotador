package com.creditas.cotador.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    SUCESSO("Solicitação realizada com sucesso", "SUCESSO"),
    ERRO("Solicitação com erro", "ERRO");

    private String mensagem;
    private String status;
}
