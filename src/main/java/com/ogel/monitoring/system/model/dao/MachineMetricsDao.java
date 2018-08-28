package com.ogel.monitoring.system.model.dao;

import com.ogel.monitoring.system.model.MachineStatuses;
import com.ogel.monitoring.system.model.ProductionInTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.List;
import java.util.Map;

@ApiModel("MachineMetrics")
public class MachineMetricsDao {

    @ApiModelProperty(notes = "Machine name", position = 1)
    private String machineName;

    @ApiModelProperty(notes = "Moment in time (milliseconds) from which metrics are calculated", position = 2)
    private long datetimeFrom;

    @ApiModelProperty(notes = "Moment in time (milliseconds) to which metrics are calculated", position = 3)
    private long datetimeTo;

    @ApiModelProperty(notes = "Total net production", position = 4)
    private int production;

    @ApiModelProperty(notes = "Percentage of scrap vs production", position = 5)
    private double scrapPercentage;

    @ApiModelProperty(notes = "Percentage of downtime", position = 6)
    private double downtimePercentage;

    @ApiModelProperty(notes = "Status of machine based on temperature", position = 7)
    private MachineStatuses machineStatus;

    @ApiModelProperty(notes = "Performance of machine", position = 8)
    private double performance;

    @ApiModelProperty(notes = "Availability of machine", position = 9)
    private double availability;

    @ApiModelProperty(notes = "Quality of machine's production", position = 10)
    private double quality;

    @ApiModelProperty(notes = "Overall Equipment Efficiency", position = 11)
    private double oee;

    @ApiModelProperty(notes = "Production of machine in time", position = 12)
    private Map<String, List<ProductionInTime>> productionInTime;

    private MachineMetricsDao(Builder builder) {
        this.machineName = builder.machineName;
        this.datetimeFrom = builder.datetimeFrom;
        this.datetimeTo = builder.datetimeTo;
        this.production = builder.production;
        this.scrapPercentage = builder.scrapPercentage;
        this.downtimePercentage = builder.downtimePercentage;
        this.machineStatus = builder.machineStatus;
        this.performance = builder.performance;
        this.availability = builder.availability;
        this.quality = builder.quality;
        this.oee = builder.oee;
        this.productionInTime = builder.productionInTime;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getMachineName() {
        return machineName;
    }

    public long getDatetimeFrom() {
        return datetimeFrom;
    }

    public long getDatetimeTo() {
        return datetimeTo;
    }

    public int getProduction() {
        return production;
    }

    public double getScrapPercentage() {
        return scrapPercentage;
    }

    public double getDowntimePercentage() {
        return downtimePercentage;
    }

    public MachineStatuses getMachineStatus() {
        return machineStatus;
    }

    public double getPerformance() {
        return performance;
    }

    public double getAvailability() {
        return availability;
    }

    public double getQuality() {
        return quality;
    }

    public double getOee() {
        return oee;
    }

    public Map<String, List<ProductionInTime>> getProductionInTime() {
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

    public static final class Builder {
        private String machineName;
        private long datetimeFrom;
        private long datetimeTo;
        private int production;
        private double scrapPercentage;
        private double downtimePercentage;
        private MachineStatuses machineStatus;
        private double performance;
        private double availability;
        private double quality;
        private double oee;
        private Map<String, List<ProductionInTime>> productionInTime;

        private Builder() {
        }

        public MachineMetricsDao build() {
            return new MachineMetricsDao(this);
        }

        public Builder machineName(String machineName) {
            this.machineName = machineName;
            return this;
        }

        public Builder datetimeFrom(long datetimeFrom) {
            this.datetimeFrom = datetimeFrom;
            return this;
        }

        public Builder datetimeTo(long datetimeTo) {
            this.datetimeTo = datetimeTo;
            return this;
        }

        public Builder production(int production) {
            this.production = production;
            return this;
        }

        public Builder scrapPercentage(double scrapPercentage) {
            this.scrapPercentage = scrapPercentage;
            return this;
        }

        public Builder downtimePercentage(double downtimePercentage) {
            this.downtimePercentage = downtimePercentage;
            return this;
        }

        public Builder machineStatus(MachineStatuses machineStatus) {
            this.machineStatus = machineStatus;
            return this;
        }

        public Builder performance(double performance) {
            this.performance = performance;
            return this;
        }

        public Builder availability(double availability) {
            this.availability = availability;
            return this;
        }

        public Builder quality(double quality) {
            this.quality = quality;
            return this;
        }

        public Builder oee(double oee) {
            this.oee = oee;
            return this;
        }

        public Builder productionInTime(Map<String, List<ProductionInTime>> productionInTime) {
            this.productionInTime = productionInTime;
            return this;
        }
    }
}
