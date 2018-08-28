package com.ogel.monitoring.system.metrics;

import com.ogel.monitoring.system.model.MachineStatuses;
import com.ogel.monitoring.system.model.dao.MachineMetricsDao;
import com.ogel.monitoring.system.model.db.MachineProduction;
import com.ogel.monitoring.system.model.query.MachineMetricsQueryParams;
import com.ogel.monitoring.system.service.data.ProductionDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TemperatureBasedMachineStateCalculationStrategy implements MetricCalculationStrategy {

    private Logger LOGGER = LoggerFactory.getLogger(TemperatureBasedMachineStateCalculationStrategy.class);

    private ProductionDataProvider productionDataProvider;

    public TemperatureBasedMachineStateCalculationStrategy(ProductionDataProvider productionDataProvider) {
        this.productionDataProvider = productionDataProvider;
    }

    @Override
    public void calculate(MachineMetricsQueryParams queryParams, MachineMetricsDao.Builder builder) {
        LOGGER.info("Calculate machine status based on temperature");
        List<MachineProduction> temperatures = productionDataProvider.findTemperatureByParams(queryParams);
        /*
           calculation logic
         */
        builder.machineStatus(MachineStatuses.GOOD);
    }
}
