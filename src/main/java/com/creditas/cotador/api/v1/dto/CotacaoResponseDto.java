package com.creditas.cotador.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "Retorno genérico", description = "Retorno utilizado para tratativas de erro ou processamento assincrono")
public class CotacaoResponseDto {
    @Schema(description = "Status da solicitação", example = "ERRO")
    private String status;

    @Schema(description = "Mensagem de retorno da requisição", example = "Solicitação com erro")
    private String mensagem;

    @Schema(description = "Lista com detalhes dos erros apresentados")
    private ArrayList<ErroDetalhe> detalhe;

    @Getter
    @Builder
    @Schema(name = "Error", description = "Retorna a lista de erros", hidden = true)
    public static class ErroDetalhe {
        @Schema(description = "Campo que está com problema", example = "valorCredito")
        private String campo;

        @Schema(description = "Descrição do erro", example = "deve ser maior que 0")
        private String descricao;
    }
}
