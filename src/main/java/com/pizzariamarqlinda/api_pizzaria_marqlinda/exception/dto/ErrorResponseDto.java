package com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErrorResponseDto(int status, String message, List<FieldErrorDto> errors) {
    public static ErrorResponseDto responseDefault(String message){
        return new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), message, List.of());
    }

    public static ErrorResponseDto responseConflict(String message){
        return new ErrorResponseDto(HttpStatus.CONFLICT.value(), message, List.of());
    }

    public static ErrorResponseDto responseAccessDenied(int status, String message){
        return new ErrorResponseDto(status, message, List.of());
    }

    public static ErrorResponseDto responseNotFound(int status, String message){
        return new ErrorResponseDto(status, message, List.of());
    }
    public static ErrorResponseDto responseUnauthorized(int status, String message){
        return new ErrorResponseDto(status, message, List.of());
    }
}
