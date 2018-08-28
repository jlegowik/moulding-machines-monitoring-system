package com.ogel.monitoring.system.metrics;

import com.ogel.monitoring.system.config.MouldingMachinesMonitoringSystemProperties;
import com.ogel.monitoring.system.model.dao.MachineMetricsDao;
import com.ogel.monitoring.system.model.query.MachineMetricsQueryParams;
import com.ogel.monitoring.system.service.data.ProductionDataProvider;
import com.ogel.monitoring.system.service.data.RuntimeDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class OverallEquipmentEfficiencyCalculationStrategy implements MetricCalculationStrategy {

    private Logger LOGGER = LoggerFactory.getLogger(ScrapPercentageCalculationStrategy.class);

    private MouldingMachinesMonitoringSystemProperties properties;
    private RuntimeDataProvider runtimeDataProvider;
    private ProductionDataProvider productionDataProvider;

    public OverallEquipmentEfficiencyCalculationStrategy(
            MouldingMachinesMonitoringSystemProperties properties,
            RuntimeDataProvider runtimeDataProvider,
            ProductionDataProvider productionDataProvider
    ) {
        this.properties = properties;
        this.runtimeDataProvider = runtimeDataProvider;
        this.productionDataProvider = productionDataProvider;
    }

    @Override
    public void calculate(MachineMetricsQueryParams queryParams, MachineMetricsDao.Builder builder) {
        LOGGER.info("Calculate OEE (Overall Equipment Efficiency)");
        int production = productionDataProvider.sumProductionInTimeframe(queryParams);
        int scrap = productionDataProvider.sumScrapInTimeframe(queryParams);

        double performance = calculatePerformance(queryParams, production);
        double availability = calculateAvailability(queryParams);
        double quality = calculateQuality(production, scrap);
        LOGGER.info("Performance: '{}', availability: '{}', quality: '{}'", performance, availability, quality);

        builder.performance(performance);
        builder.availability(availability);
        builder.quality(quality);
        builder.oee(performance * availability * quality);
    }

    private double calculatePerformance(MachineMetricsQueryParams queryParams, int production) {
        LOGGER.info("Calculate performance");
        Duration diff = Duration.between(queryParams.getParsedDateTimeFrom(), queryParams.getParsedDateTimeTo());
        double norm = (double) diff.toMillis() * properties.getNormGrossProductionBricksPerHour() / 3600000;
        LOGGER.info("Production norm: '{}', production: '{}'", norm, production);

        return production / norm;
    }

    private double calculateAvailability(MachineMetricsQueryParams queryParams) {
        LOGGER.info("Calculate availability");
        long uptime = runtimeDataProvider.sumUptimeInTimeframe(queryParams);
        Duration diff = Duration.between(queryParams.getParsedDateTimeFrom(), queryParams.getParsedDateTimeTo());
        double norm = diff.toMillis() * properties.getNormUptime();
        LOGGER.info("Availability norm: '{}', uptime: '{}'", norm, uptime);

        return uptime / norm;
    }

    private double calculateQuality(int production, int scrap) {
        LOGGER.info("Calculate quality. Production: '{}', scrap: '{}'", production, scrap);
        return (production - scrap) / (double) production;
    }
}