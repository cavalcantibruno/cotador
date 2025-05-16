package com.creditas.cotador.api.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Schema(name = "Requisição da cotação", description = "Dados mínimos para que uma cotação seja realziada")
public class SimulacaoRequestDto {

    @Schema(description = "Valor do crédito solicitado", example = "10000.00")
    @Positive
    private Double valorCredito;

    @Schema(description = "Data de nascimento em formato yyyy-MM-dd", example = "1985-09-20")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String dataNascimento;

    @Schema(description = "Prazo em meses para pagamento, minimo 18 meses", example = "24")
    @Min(18)
    private Integer prazo;

    @Schema(description = "Email para recebimento da proposta", example = "cliente@creditas.com")
    @NotBlank
    @Email
    private String email;

    @Schema(description = "Nome do cliente", example = "Anakin")
    private String nome;

}
