package com.hamzakhan.exception;

public class RequestValidationException extends RuntimeException{

    public RequestValidationException(String message) {
        super(message);
    }
    
}
