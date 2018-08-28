package com.ogel.monitoring.system.model.db;

import com.ogel.monitoring.system.model.MachineVariables;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import java.time.OffsetDateTime;

public class MachineProduction {

    private int id;
    private String machineName;
    private MachineVariables variableName;
    private OffsetDateTime datetimeFrom;
    private OffsetDateTime datetimeTo;
    private double value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public MachineVariables getVariableName() {
        return variableName;
    }

    public void setVariableName(MachineVariables variableName) {
        this.variableName = variableName;
    }

    public OffsetDateTime getDatetimeFrom() {
        return datetimeFrom;
    }

    public void setDatetimeFrom(OffsetDateTime datetimeFrom) {
        this.datetimeFrom = datetimeFrom;
    }

    public OffsetDateTime getDatetimeTo() {
        return datetimeTo;
    }

    public void setDatetimeTo(OffsetDateTime datetimeTo) {
        this.datetimeTo = datetimeTo;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
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
