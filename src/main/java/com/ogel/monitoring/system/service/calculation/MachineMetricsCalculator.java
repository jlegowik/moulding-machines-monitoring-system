package com.ogel.monitoring.system.service.calculation;

import com.ogel.monitoring.system.metrics.MetricsCalculationsChain;
import com.ogel.monitoring.system.model.dao.MachineMetricsDao;
import com.ogel.monitoring.system.model.query.MachineMetricsQueryParams;
import com.ogel.monitoring.system.service.validation.MachineMetricsQueryParamsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MachineMetricsCalculator {

    private Logger LOGGER = LoggerFactory.getLogger(MachineMetricsCalculator.class);

    private MachineMetricsQueryParamsValidator queryParamsValidator;
    private MetricsCalculationsChain metricsCalculationsChain;

    public MachineMetricsCalculator(
            MachineMetricsQueryParamsValidator queryParamsValidator,
            MetricsCalculationsChain metricsCalculationsChain
    ) {
        this.queryParamsValidator = queryParamsValidator;
        this.metricsCalculationsChain = metricsCalculationsChain;
    }

    public MachineMetricsDao calculate(MachineMetricsQueryParams queryParams) {
        queryParamsValidator.validate(queryParams);
        LOGGER.info("Calculate metrics for machine '{}'", queryParams.getMachineName());
        MachineMetricsDao.Builder builder = MachineMetricsDao.newBuilder();
        enrichWithBasicInfo(queryParams, builder);
        metricsCalculationsChain.calculate(queryParams, builder);

        return builder.build();
    }

    private void enrichWithBasicInfo(MachineMetricsQueryParams queryParams, MachineMetricsDao.Builder builder) {
        builder.machineName(queryParams.getMachineName())
                .datetimeFrom(queryParams.getDateTimeFrom())
                .datetimeTo(queryParams.getDateTimeTo());
    }
}
