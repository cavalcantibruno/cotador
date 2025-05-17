package com.creditas.cotador.domain.service.impl;

import com.creditas.cotador.api.v1.dto.SimulacaoRequestDto;
import com.creditas.cotador.api.v1.dto.SimulacaoResponseDto;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.UUID;


 class EnviarEmailServiceImplTest {


    @Test
    void enviarCotacaoPorEmail_deveEnviarEmailCorretamente() throws Exception {
        JavaMailSender javaMailSender = mock(JavaMailSender.class);
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        EnviarEmailServiceImpl service = new EnviarEmailServiceImpl(javaMailSender);

        SimulacaoResponseDto responseDto = getSimulacaoResponseDto();
        SimulacaoRequestDto requestDto = getSimulacaoRequestDto();

        doNothing().when(javaMailSender).send(any(MimeMessage.class));
        
        service.enviarCotacaoPorEmail(responseDto, requestDto);

        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    void enviarCotacaoPorEmail_quandoOcorreErro_deveLancarExcecao() {
        // Arrange
        JavaMailSender javaMailSender = mock(JavaMailSender.class);
        when(javaMailSender.createMimeMessage()).thenThrow(new RuntimeException("Erro ao criar mensagem"));

        EnviarEmailServiceImpl service = new EnviarEmailServiceImpl(javaMailSender);

        SimulacaoResponseDto responseDto = getSimulacaoResponseDto();
        SimulacaoRequestDto requestDto = getSimulacaoRequestDto();

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
            service.enviarCotacaoPorEmail(responseDto, requestDto)
        );
    }

     private static SimulacaoRequestDto getSimulacaoRequestDto() {
         return new SimulacaoRequestDto(50000.00, "1985-09-20",  24, "padmeamidala84@outlook.com", "Padmé Amidala");
     }

     private static SimulacaoResponseDto getSimulacaoResponseDto() {
         return SimulacaoResponseDto.builder()
                 .idCotacao(UUID.randomUUID())
                 .prazo(24)
                 .taxaJuros(3.0)
                 .valorSolicitado(new BigDecimal("50000.00"))
                 .valorParcelaMensal(new BigDecimal("2149.06"))
                 .valorTotalComJuros(new BigDecimal("51577.44"))
                 .jurosTotal(new BigDecimal("1577.44"))
                 .build();
     }
 }