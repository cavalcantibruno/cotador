package com.creditas.cotador.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "Retorno da cotação", description = "Objeto que representa o retorno de uma cotação")
public class SimulacaoResponseDto {
    @Schema(description = "Identificador Unico da Cotação", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID idCotacao;

    @Schema(description = "Retorna o mesmo prazo enviado na requisição", example = "24")
    private Integer prazo;
    @Schema(description = "A taxa de juros deve ser calculada com base na faixa etária do cliente \n" +
            "Até 25 anos: 5% ao ano\n" +
            "○ De 26 a 40 anos: 3% ao ano\n" +
            "○ De 41 a 60 anos: 2% ao ano\n" +
            "○ Acima de 60 anos: 4% ao ano", example = "3.0")
    private Double taxaJuros;
    @Schema(description = "Subtração entre o valor total com juros e o valor solicitado", example = "1577.44")
    private BigDecimal jurosTotal;
    @Schema(description = "Capital solicitado", example = "50000.00")
    private BigDecimal valorSolicitado;
    @Schema(description = "Valor que será pago mensalmente", example = "2149.06")
    private BigDecimal valorParcelaMensal;
    @Schema(description = "Valor total pago considerando o juros ", example = "51577.44")
    private BigDecimal valorTotalComJuros;
}
