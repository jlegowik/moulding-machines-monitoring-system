package com.ogel.monitoring.system.service.validation;

import com.ogel.common.error.RestValidationError;

import java.util.List;

public interface Check<T> {

    void validate(T obj, List<RestValidationError> errors);

    default RestValidationError createRestValidationError(String field, String message) {
        return new RestValidationError(field, message);
    }
}
