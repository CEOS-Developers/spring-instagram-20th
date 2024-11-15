package com.ceos20.instagram.global.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends RuntimeException{

    private final ExceptionCode exceptionCode;

    public ConflictException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public HttpStatus getStatus() {
        return exceptionCode.getStatus();
    }

    public String getMessage() {
        return exceptionCode.getMessage();
    }
}
