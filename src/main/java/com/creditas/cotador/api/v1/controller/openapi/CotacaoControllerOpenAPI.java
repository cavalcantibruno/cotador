package com.creditas.cotador.api.v1.controller.openapi;

import com.creditas.cotador.api.v1.dto.CotacaoResponseDto;
import com.creditas.cotador.api.v1.dto.SimulacaoRequestDto;
import com.creditas.cotador.api.v1.dto.SimulacaoResponseDto;
import com.creditas.cotador.domain.enums.Taxas;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

@Tag(name = "Simular Cotação", description = "Simular um empréstimo")
public interface CotacaoControllerOpenAPI {

    @Operation( summary = "Realiza simulação de emprestimo de forma sincrona",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Solicitação realizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro ao realizar solicitação")
            }
    )
    SimulacaoResponseDto simulacaoDeCreditoSync(
            @RequestBody @Valid SimulacaoRequestDto request,
            @Parameter(name = "taxa", description = "Informa o tipo de taxa que será utilizado na cotação", example = "TAXA_FIXA") Taxas taxa);

    @Operation( summary = "Realiza simulação de emprestimo em lote de forma assincrona",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Solicitação realizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro ao realizar solicitação")
            }
    )
    CotacaoResponseDto simulacaoDeCreditoAsync(@RequestBody List<SimulacaoRequestDto> request);
}
