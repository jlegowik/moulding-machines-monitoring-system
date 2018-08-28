package com.ogel.monitoring.system.metrics;

import com.ogel.monitoring.system.model.dao.MachineMetricsDao;
import com.ogel.monitoring.system.model.query.MachineMetricsQueryParams;

public interface MetricCalculationStrategy {

    void calculate(MachineMetricsQueryParams queryParams, MachineMetricsDao.Builder builder);
}
