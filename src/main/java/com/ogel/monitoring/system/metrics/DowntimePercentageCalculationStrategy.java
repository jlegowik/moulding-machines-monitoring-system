package com.ogel.monitoring.system.metrics;

import com.ogel.monitoring.system.model.dao.MachineMetricsDao;
import com.ogel.monitoring.system.model.query.MachineMetricsQueryParams;
import com.ogel.monitoring.system.service.data.RuntimeDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class DowntimePercentageCalculationStrategy implements MetricCalculationStrategy {

    private Logger LOGGER = LoggerFactory.getLogger(DowntimePercentageCalculationStrategy.class);

    private RuntimeDataProvider runtimeDataProvider;

    public DowntimePercentageCalculationStrategy(RuntimeDataProvider runtimeDataProvider) {
        this.runtimeDataProvider = runtimeDataProvider;
    }

    @Override
    public void calculate(MachineMetricsQueryParams queryParams, MachineMetricsDao.Builder builder) {
        LOGGER.info("Calculate downtime percentage");
        double testedPeriod =  Duration.between(queryParams.getParsedDateTimeFrom(), queryParams.getParsedDateTimeTo()).toMillis();
        builder.downtimePercentage(runtimeDataProvider.sumDowntimeInTimeframe(queryParams) / testedPeriod);
    }
}
