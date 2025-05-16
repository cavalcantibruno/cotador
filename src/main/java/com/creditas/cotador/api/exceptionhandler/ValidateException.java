package com.creditas.cotador.api.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
@AllArgsConstructor
public class ValidateException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private BindingResult bindingResult;
}
