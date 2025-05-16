package com.creditas.cotador.domain.service.impl;

import com.creditas.cotador.api.v1.dto.SimulacaoRequestDto;
import com.creditas.cotador.api.v1.dto.SimulacaoResponseDto;
import com.creditas.cotador.domain.service.EnviarEmailService;
import com.creditas.cotador.utils.EmailTemplate;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class EnviarEmailServiceImpl implements EnviarEmailService {

    private JavaMailSender javaMailSender;

    public void enviarCotacaoPorEmail(SimulacaoResponseDto info, SimulacaoRequestDto req) {
        String titulo = "A simulação do seu empréstimo chegou \uD83C\uDF89";

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom("no-reply@creditas.com");
            mimeMessageHelper.setTo(req.getEmail());

            mimeMessageHelper.setSubject(titulo);
            mimeMessageHelper.setText(EmailTemplate.emailDeSimulacao(info, req), true);

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.info(e.getMessage(), e.getCause());
        }

    }
}
