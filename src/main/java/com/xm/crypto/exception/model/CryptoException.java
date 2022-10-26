package com.xm.crypto.exception.model;

import org.springframework.http.HttpStatus;

import java.util.List;

public class CryptoException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String errorCode;
    private final String errorMessage;
    private final String errorType;
    private final HttpStatus httpStatus;
    private String arg;
    private List<Object> args;

    public CryptoException(String errorCode, String errorMessage, String errorType, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorType = errorType;
    }

    public CryptoException(String errorCode, String arg, String errorMessage, String errorType, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorType = errorType;
        this.arg = arg;
    }

    public CryptoException(String errorCode, List<Object> args, String errorMessage, String errorType, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorType = errorType;
        this.args = args;
    }

    public String getArg() {
        return this.arg;
    }

    public List<Object> getArgs() {
        return this.args;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public String getErrorType() {
        return this.errorType;
    }
}
