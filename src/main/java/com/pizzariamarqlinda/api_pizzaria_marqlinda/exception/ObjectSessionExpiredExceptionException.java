package com.pizzariamarqlinda.api_pizzaria_marqlinda.exception;

public class ObjectSessionExpiredExceptionException extends RuntimeException{
    public ObjectSessionExpiredExceptionException(String message){
        super(message);
    }
}
