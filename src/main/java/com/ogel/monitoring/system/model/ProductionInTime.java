package com.ogel.monitoring.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@ApiModel("ProductionInTime")
public class ProductionInTime {

    @ApiModelProperty(notes = "Hour in which NET production was calculated", position = 1)
    private int hour;

    @ApiModelProperty(notes = "Calculated NET production", position = 1)
    private int value;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static ProductionInTime of(
            int hour,
            int value
    ) {
        ProductionInTime productionInTime = new ProductionInTime();
        productionInTime.setHour(hour);
        productionInTime.setValue(value);
        return productionInTime;
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
