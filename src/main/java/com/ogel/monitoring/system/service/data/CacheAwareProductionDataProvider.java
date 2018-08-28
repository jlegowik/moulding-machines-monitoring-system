package com.ogel.monitoring.system.service.data;

import com.ogel.monitoring.system.model.MachineVariables;
import com.ogel.monitoring.system.model.db.MachineProduction;
import com.ogel.monitoring.system.model.query.MachineMetricsQueryParams;
import com.ogel.monitoring.system.repository.MachineProductionRepository;
import com.ogel.monitoring.system.service.cache.Cache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class CacheAwareProductionDataProvider implements ProductionDataProvider {

    private Cache<MachineProduction> cache;
    private MachineProductionRepository productionRepository;

    public CacheAwareProductionDataProvider(
            @Qualifier("machine-production-data-cache") Cache<MachineProduction> cache,
            MachineProductionRepository productionRepository
    ) {
        this.cache = cache;
        this.productionRepository = productionRepository;
    }

    public List<MachineProduction> findByParams(MachineMetricsQueryParams queryParams) {
        return findByParamsAndCache(queryParams, Optional.empty());
    }

    public List<MachineProduction> findProductionByParams(MachineMetricsQueryParams queryParams) {
        return findByParamsAndCache(queryParams, Optional.of(MachineVariables.PRODUCTION));
    }

    public List<MachineProduction> findScrapByParams(MachineMetricsQueryParams queryParams) {
        return findByParamsAndCache(queryParams, Optional.of(MachineVariables.SCRAP));
    }

    public List<MachineProduction> findTemperatureByParams(MachineMetricsQueryParams queryParams) {
        return findByParamsAndCache(queryParams, Optional.of(MachineVariables.CORE_TEMPERATURE));
    }

    private List<MachineProduction> findByParamsAndCache(MachineMetricsQueryParams queryParams, Optional<MachineVariables> machineVariableName) {
        return cache.isInCache(queryParams) ? cache.getFromCache(queryParams)
                : cache.cacheAndReturn(productionRepository.findBy(
                queryParams.getMachineName(),
                queryParams.getParsedDateTimeFrom(),
                queryParams.getParsedDateTimeTo(),
                machineVariableName
        ));
    }

    public int sumProductionInTimeframe(MachineMetricsQueryParams queryParams) {
        return findProductionByParams(queryParams)
                .stream()
                .mapToInt(e -> Double.valueOf(e.getValue()).intValue())
                .sum();
    }

    public int sumScrapInTimeframe(MachineMetricsQueryParams queryParams) {
        return findScrapByParams(queryParams)
                .stream()
                .mapToInt(e -> Double.valueOf(e.getValue()).intValue())
                .sum();
    }

    private List<MachineProduction> cacheAndReturn(List<MachineProduction> resultsToCache) {
        /*
            Logic to cache data
         */
        return resultsToCache;
    }

    private List<MachineProduction> getFromCache(MachineMetricsQueryParams queryParams) {
        /*
            Logic to get data from cache
         */
        return Collections.emptyList();
    }
}
