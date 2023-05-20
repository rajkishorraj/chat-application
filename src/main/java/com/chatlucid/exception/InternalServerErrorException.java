package com.chatlucid.exception;

public class InternalServerErrorException extends RuntimeException {

    public InternalServerErrorException(Throwable throwable) {
        super(throwable);
    }

    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

