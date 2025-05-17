package com.creditas.cotador.api.v1.controller;

import com.creditas.cotador.api.v1.controller.openapi.CotacaoControllerOpenAPI;
import com.creditas.cotador.api.v1.dto.CotacaoResponseDto;
import com.creditas.cotador.api.v1.dto.SimulacaoRequestDto;
import com.creditas.cotador.api.v1.dto.SimulacaoResponseDto;
import com.creditas.cotador.domain.enums.Status;
import com.creditas.cotador.domain.enums.Taxas;
import com.creditas.cotador.domain.service.CotacaoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/cotacao")
public class CotacaoController implements CotacaoControllerOpenAPI {

    private final CotacaoService cotacaoService;

    @Override
    @PostMapping("/sync/simular")
    public SimulacaoResponseDto simulacaoDeCreditoSync(
            @RequestBody @Valid SimulacaoRequestDto request,
            @RequestParam(name = "taxa", defaultValue = "TAXA_FIXA", required = false) Taxas taxa)  {
        return cotacaoService.simulacaoDeCreditoSync(request, taxa);
    }

    @Override
    @PostMapping("/async/simular")
    public CotacaoResponseDto simulacaoDeCreditoAsync(@RequestBody List<SimulacaoRequestDto> request)  {
        cotacaoService.simulacaoDeCreditoAsync(request);
        return CotacaoResponseDto.builder()
                .status(Status.SUCESSO.getStatus())
                .mensagem(Status.SUCESSO.getMensagem())
                .build();
    }
}
