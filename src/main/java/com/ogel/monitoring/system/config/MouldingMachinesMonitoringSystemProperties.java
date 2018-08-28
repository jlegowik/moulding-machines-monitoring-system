package com.ogel.monitoring.system.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ogel.moulding-machines-monitoring-system")
public class MouldingMachinesMonitoringSystemProperties {

    private int normGrossProductionBricksPerHour;
    private double normUptime;

    public int getNormGrossProductionBricksPerHour() {
        return normGrossProductionBricksPerHour;
    }

    public void setNormGrossProductionBricksPerHour(int normGrossProductionBricksPerHour) {
        this.normGrossProductionBricksPerHour = normGrossProductionBricksPerHour;
    }

    public double getNormUptime() {
        return normUptime;
    }

    public void setNormUptime(double normUptime) {
        this.normUptime = normUptime;
    }
}
