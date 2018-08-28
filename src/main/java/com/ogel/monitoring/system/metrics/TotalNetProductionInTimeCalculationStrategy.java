package com.ogel.monitoring.system.metrics;

import com.ogel.common.date.DateUtils;
import com.ogel.monitoring.system.model.MachineVariables;
import com.ogel.monitoring.system.model.ProductionInTime;
import com.ogel.monitoring.system.model.dao.MachineMetricsDao;
import com.ogel.monitoring.system.model.db.MachineProduction;
import com.ogel.monitoring.system.model.query.MachineMetricsQueryParams;
import com.ogel.monitoring.system.service.data.ProductionDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class TotalNetProductionInTimeCalculationStrategy implements MetricCalculationStrategy {

    private Logger LOGGER = LoggerFactory.getLogger(TotalNetProductionInTimeCalculationStrategy.class);

    private ProductionDataProvider productionDataProvider;

    public TotalNetProductionInTimeCalculationStrategy(ProductionDataProvider productionDataProvider) {
        this.productionDataProvider = productionDataProvider;
    }

    @Override
    public void calculate(MachineMetricsQueryParams queryParams, MachineMetricsDao.Builder builder) {
        LOGGER.info("Calculate total net production in time");
        List<MachineProduction> production = productionDataProvider.findByParams(queryParams);
        Map<String, Map<Integer, List<MachineProduction>>> productionPerDayAndHour = production.stream()
                .collect(groupingBy(e -> DateUtils.getStringDate(e.getDatetimeFrom()), groupingBy(w -> w.getDatetimeFrom().getHour())));

        Map<String, List<ProductionInTime>> calculatedMetrics = new LinkedHashMap<>();
        productionPerDayAndHour.forEach((date, productionPerHour) -> {
            List<ProductionInTime> productionInTimes = new ArrayList<>();
            productionPerHour.forEach((hour, productionInHour) -> {
                int productionInHourSum = productionInHour.stream()
                        .filter(e -> e.getVariableName().equals(MachineVariables.PRODUCTION))
                        .mapToInt(e -> (int) e.getValue())
                        .sum();
                int scrapInHourSum = productionInHour.stream()
                        .filter(e -> e.getVariableName().equals(MachineVariables.SCRAP))
                        .mapToInt(e -> (int) e.getValue())
                        .sum();
                productionInTimes.add(ProductionInTime.of(hour, productionInHourSum - scrapInHourSum));
            });
            calculatedMetrics.put(date, productionInTimes);
        });

        builder.productionInTime(calculatedMetrics.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new)));
    }
}
