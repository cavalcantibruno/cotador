package com.creditas.cotador.api.v1.controller;

import com.creditas.cotador.api.v1.controller.openapi.CotacaoControllerOpenAPI;
import com.creditas.cotador.api.v1.dto.CotacaoResponseDto;
import com.creditas.cotador.api.v1.dto.SimulacaoRequestDto;
import com.creditas.cotador.api.v1.dto.SimulacaoResponseDto;
import com.creditas.cotador.domain.enums.Status;
import com.creditas.cotador.domain.service.CotacaoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/cotacao")
public class CotacaoController implements CotacaoControllerOpenAPI {

    private final CotacaoService cotacaoService;

    @Override
    @PostMapping("/sync/simular")
    public SimulacaoResponseDto simulacaoDeCreditoSync(@RequestBody @Valid SimulacaoRequestDto request)  {
        return cotacaoService.simulacaoDeCreditoSync(request);
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
