package com.ogel.common.error;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@ApiModel("RestValidationError")
public class RestValidationError {

    @ApiModelProperty(notes = "Name of field with invalid value", position = 1, example = "from")
    private String field;

    @ApiModelProperty(notes = "Message of validation error", position = 2, example = "Required Long parameter 'from' is not present")
    private String message;

    public RestValidationError() {
    }

    public RestValidationError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static RestValidationError of(String field, String message) {
        return new RestValidationError(field, message);
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