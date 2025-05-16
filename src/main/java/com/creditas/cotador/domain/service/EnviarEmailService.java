package com.creditas.cotador.domain.service;

import com.creditas.cotador.api.v1.dto.SimulacaoRequestDto;
import com.creditas.cotador.api.v1.dto.SimulacaoResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface EnviarEmailService {
    void enviarCotacaoPorEmail(SimulacaoResponseDto info, SimulacaoRequestDto req);
}
