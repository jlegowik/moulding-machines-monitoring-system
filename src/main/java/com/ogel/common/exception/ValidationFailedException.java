package com.ogel.common.exception;

import com.ogel.common.error.RestValidationError;

import java.util.ArrayList;
import java.util.List;

public class ValidationFailedException extends RuntimeException {

    private List<RestValidationError> restValidationErrors = new ArrayList<>();

    public ValidationFailedException(String message, List<RestValidationError> fieldErrors) {
        super(message);
        this.restValidationErrors = fieldErrors;
    }

    public List<RestValidationError> getErrors() {
        return restValidationErrors;
    }
}