package com.ogel.monitoring.system.model.query;

import com.ogel.common.date.DateUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.time.OffsetDateTime;

public class MachineMetricsQueryParams {

    public enum Params {
        MACHINE_NAME("machineName"),
        FROM("from"),
        TO("to");

        private final String field;

        Params(final String field) {
            this.field = field;
        }

        public String fieldName() {
            return field;
        }
    }

    private String machineName;
    private Long dateTimeFrom;
    private Long dateTimeTo;

    private MachineMetricsQueryParams(Builder builder) {
        this.machineName = builder.machineName;
        this.dateTimeFrom = builder.dateTimeFrom;
        this.dateTimeTo = builder.dateTimeTo;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getMachineName() {
        return machineName;
    }

    public Long getDateTimeFrom() {
        return dateTimeFrom;
    }

    public Long getDateTimeTo() {
        return dateTimeTo;
    }

    public OffsetDateTime getParsedDateTimeFrom() {
        return DateUtils.fromMills(dateTimeFrom);
    }

    public OffsetDateTime getParsedDateTimeTo() {
        return DateUtils.fromMills(dateTimeTo);
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

    public static final class Builder {
        private String machineName;
        private Long dateTimeFrom;
        private Long dateTimeTo;

        private Builder() {
        }

        public MachineMetricsQueryParams build() {
            return new MachineMetricsQueryParams(this);
        }

        public Builder machineName(String machineName) {
            this.machineName = machineName;
            return this;
        }

        public Builder dateTimeFrom(Long dateTimeFrom) {
            this.dateTimeFrom = dateTimeFrom;
            return this;
        }

        public Builder dateTimeTo(Long dateTimeTo) {
            this.dateTimeTo = dateTimeTo;
            return this;
        }
    }

}
