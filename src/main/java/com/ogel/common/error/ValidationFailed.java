package com.ogel.common.error;

public class ValidationFailed extends RestError {

    public final static int HTTP_STATUS_CODE = 400;
    public final static String CODE = "VALIDATION_FAILED_ERROR";

    public ValidationFailed() {
        super(CODE);
    }

    public ValidationFailed(String message) {
        super(CODE, message);
    }
}