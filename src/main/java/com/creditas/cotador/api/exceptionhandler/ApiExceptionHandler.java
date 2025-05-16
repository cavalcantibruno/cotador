package com.creditas.cotador.api.exceptionhandler;

import com.creditas.cotador.api.v1.dto.CotacaoResponseDto;
import com.creditas.cotador.domain.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CotacaoResponseDto response = cotacaoResponse(null, Status.ERRO.getMensagem());
        return handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
    }

    public ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult, HttpHeaders headers,
                                                           HttpStatusCode status, WebRequest request) {
        ArrayList<CotacaoResponseDto.ErroDetalhe> detalhes = new ArrayList<>();

        for (ObjectError objectError : bindingResult.getAllErrors()) {
            String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

            String name = objectError.getObjectName();

            if (objectError instanceof FieldError) {
                name = ((FieldError) objectError).getField();
            }

            detalhes.add(CotacaoResponseDto.ErroDetalhe.builder().campo(name).descricao(message).build());
        }

        CotacaoResponseDto response = cotacaoResponse(detalhes, Status.ERRO.getMensagem());
        return handleExceptionInternal(ex, response, headers, status, request);
    }

    @ExceptionHandler({ ValidateException.class })
    public ResponseEntity<Object> handleValidacaoException(ValidateException ex, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        CotacaoResponseDto response = cotacaoResponse(null, Status.ERRO.getMensagem());
        return handleExceptionInternal(ex, response, headers, status, request);
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatusCode status, WebRequest request) {
        if(body == null) {
            body = cotacaoResponse(null, Status.ERRO.getMensagem());
        } else if (body instanceof String) {
            body = cotacaoResponse(null,  Status.ERRO.getMensagem());
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private CotacaoResponseDto cotacaoResponse(ArrayList<CotacaoResponseDto.ErroDetalhe> detalhe, String mensagem) {
        return CotacaoResponseDto.builder()
                .status(Status.ERRO.getStatus())
                .mensagem(mensagem)
                .detalhe(detalhe)
                .build();
    }
}
