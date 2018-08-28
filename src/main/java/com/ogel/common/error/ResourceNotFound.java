package com.ogel.common.error;

public class ResourceNotFound extends RestError {

    public final static int HTTP_STATUS_CODE = 404;
    public final static String CODE = "RESOURCE_NOT_FOUND_ERROR";

    public ResourceNotFound() {
        super(CODE);
    }

    public ResourceNotFound(String message) {
        super(CODE, message);
    }
}