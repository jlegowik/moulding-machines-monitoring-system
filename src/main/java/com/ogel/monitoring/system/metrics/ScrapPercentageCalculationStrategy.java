package com.ogel.monitoring.system.metrics;

import com.ogel.monitoring.system.model.dao.MachineMetricsDao;
import com.ogel.monitoring.system.model.query.MachineMetricsQueryParams;
import com.ogel.monitoring.system.service.data.ProductionDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScrapPercentageCalculationStrategy implements MetricCalculationStrategy {

    private Logger LOGGER = LoggerFactory.getLogger(ScrapPercentageCalculationStrategy.class);

    private ProductionDataProvider productionDataProvider;

    public ScrapPercentageCalculationStrategy(ProductionDataProvider productionDataProvider) {
        this.productionDataProvider = productionDataProvider;
    }

    @Override
    public void calculate(MachineMetricsQueryParams queryParams, MachineMetricsDao.Builder builder) {
        LOGGER.info("Calculate scrap percentage");
        double productionSum = productionDataProvider.sumProductionInTimeframe(queryParams);
        double scrapSum = productionDataProvider.sumScrapInTimeframe(queryParams);

        builder.scrapPercentage(productionSum == 0 ? 0 : scrapSum / productionSum);
    }
}
