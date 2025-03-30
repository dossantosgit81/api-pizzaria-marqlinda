package com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.handler;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.*;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.dto.ErrorResponseDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.dto.FieldErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
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
        return new ErrorResponseDto(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Há campos inválidos.", errors);
    }

    @ExceptionHandler(ObjectAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto objectAlreadyExistsException(ObjectAlreadyExists ex){
        return ErrorResponseDto.responseConflict(ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponseDto accessDeniedException(AccessDeniedException ex){
        return ErrorResponseDto.responseAccessDenied(HttpStatus.FORBIDDEN.value(), "Acesso negado.");
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto objectNotFoundException(ObjectNotFoundException ex){
        return ErrorResponseDto.responseNotFound(HttpStatus.NOT_FOUND.value(),ex.getMessage());
    }

    @ExceptionHandler(ObjectSessionExpiredExceptionException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseDto objectSessionExpiredExceptionException(ObjectSessionExpiredExceptionException ex){
        return ErrorResponseDto.responseNotFound(HttpStatus.UNAUTHORIZED.value(),ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponseDto badCredentialsException(BadCredentialsException ex){
        return ErrorResponseDto.responseNotFound(HttpStatus.FORBIDDEN.value(),ex.getMessage());
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto fileNotFoundException(FileNotFoundException ex){
        return ErrorResponseDto.responseNotFound(HttpStatus.NOT_FOUND.value(),ex.getMessage());
    }

    @ExceptionHandler(FileStorageException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto fileStorageException(FileNotFoundException ex){
        return ErrorResponseDto.responseNotFound(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage());
    }

    @ExceptionHandler(InvalidFormatImageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto invalidFormatImageException(InvalidFormatImageException ex){
        return ErrorResponseDto.responseNotFound(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(BusinessLogicException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto businessLogicException(BusinessLogicException ex){
        return ErrorResponseDto.responseNotFound(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

}
