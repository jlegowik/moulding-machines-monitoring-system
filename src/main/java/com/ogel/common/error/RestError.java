package com.ogel.common.error;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.ArrayList;
import java.util.List;

@ApiModel("RestError")
public abstract class RestError {

    @ApiModelProperty(notes = "Code of error", position = 1, example = "VALIDATION_FAILED_ERROR")
    private String code;

    @ApiModelProperty(notes = "Message of error", position = 2, example = "Request validation failed")
    private String message;

    @ApiModelProperty(notes = "Request fields validation errors", position = 3)
    private List<RestValidationError> errors = new ArrayList<>();

    public RestError(String code) {
        this.code = code;
    }

    public RestError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<RestValidationError> getErrors() {
        return errors;
    }

    public void addError(RestValidationError error) {
        this.errors.add(error);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}