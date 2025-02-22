package com.pizzariamarqlinda.api_pizzaria_marqlinda.resource.handler;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.ErrorResponseDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.FieldErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponseDto methodNotValidArgumentException(MethodArgumentNotValidException ex){
        List<FieldErrorDto> errors = ex.getFieldErrors().stream().map(e -> new FieldErrorDto(e.getField(), e.getDefaultMessage())).toList();
        return new ErrorResponseDto(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação.", errors);
    }

}
