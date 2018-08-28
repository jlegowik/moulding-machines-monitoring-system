package com.ogel.common.error;

public class AuthorizationFailed extends RestError {

    public final static int HTTP_STATUS_CODE = 403;
    public final static String CODE = "AUTHORIZATION_FAILED_ERROR";

    public AuthorizationFailed() {
        super(CODE);
    }

    public AuthorizationFailed(String message) {
        super(CODE, message);
    }
}