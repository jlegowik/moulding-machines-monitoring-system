package com.ogel.common.exception;

public class ApiException extends RuntimeException {

    private final int errorCode;

    public ApiException(final String message, final int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getCode() {
        return errorCode;
    }
}