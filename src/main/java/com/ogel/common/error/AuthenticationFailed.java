package com.ogel.common.error;

public class AuthenticationFailed extends RestError {

    public final static int HTTP_STATUS_CODE = 401;
    public final static String CODE = "AUTHENTICATION_FAILED_ERROR";

    public AuthenticationFailed() {
        super(CODE);
    }

    public AuthenticationFailed(String message) {
        super(CODE, message);
    }
}