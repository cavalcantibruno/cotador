package com.creditas.cotador.domain.service;

import com.creditas.cotador.api.v1.dto.SimulacaoRequestDto;
import com.creditas.cotador.api.v1.dto.SimulacaoResponseDto;
import com.creditas.cotador.domain.enums.Taxas;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CotacaoService {
    SimulacaoResponseDto simulacaoDeCreditoSync(SimulacaoRequestDto request, Taxas taxa);
    void simulacaoDeCreditoAsync(List<SimulacaoRequestDto> request);
}
