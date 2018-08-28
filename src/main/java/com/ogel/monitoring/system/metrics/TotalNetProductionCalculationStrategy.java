package com.ogel.monitoring.system.metrics;

import com.ogel.monitoring.system.model.dao.MachineMetricsDao;
import com.ogel.monitoring.system.model.query.MachineMetricsQueryParams;
import com.ogel.monitoring.system.service.data.ProductionDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TotalNetProductionCalculationStrategy implements MetricCalculationStrategy {

    private Logger LOGGER = LoggerFactory.getLogger(TotalNetProductionCalculationStrategy.class);

    private ProductionDataProvider productionDataProvider;

    public TotalNetProductionCalculationStrategy(ProductionDataProvider productionDataProvider) {
        this.productionDataProvider = productionDataProvider;
    }

    @Override
    public void calculate(MachineMetricsQueryParams queryParams, MachineMetricsDao.Builder builder) {
        LOGGER.info("Calculate total net production");
        builder.production(productionDataProvider.sumProductionInTimeframe(queryParams) - productionDataProvider.sumScrapInTimeframe(queryParams));
    }
}
