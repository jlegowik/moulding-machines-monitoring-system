package com.ogel.monitoring.system.metrics;

import com.ogel.monitoring.system.model.dao.MachineMetricsDao;
import com.ogel.monitoring.system.model.query.MachineMetricsQueryParams;

import java.util.ArrayList;
import java.util.List;

public class MetricsCalculationsChain {

    private List<MetricCalculationStrategy> metricCalculationStrategies = new ArrayList<>();

    public void calculate(MachineMetricsQueryParams queryParams, MachineMetricsDao.Builder builder) {
        metricCalculationStrategies.forEach(e -> e.calculate(queryParams, builder));
    }

    public MetricsCalculationsChain registerStrategy(MetricCalculationStrategy strategy) {
        metricCalculationStrategies.add(strategy);
        return this;
    }
}
