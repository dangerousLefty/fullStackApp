package com.hamzaKhan.fullStackbackEnd.exception;

public class RequestValidationException extends RuntimeException{
    public RequestValidationException(String message) {
        super(message);
    }
}
