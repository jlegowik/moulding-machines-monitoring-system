package com.ogel.monitoring.system.service.data;

import com.ogel.monitoring.system.model.db.MachineRuntime;
import com.ogel.monitoring.system.model.query.MachineMetricsQueryParams;
import com.ogel.monitoring.system.repository.MachineRuntimeRepository;
import com.ogel.monitoring.system.service.cache.Cache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CacheAwareRuntimeDataProvider implements RuntimeDataProvider {

    private Cache<MachineRuntime> cache;
    private MachineRuntimeRepository runtimeRepository;

    public CacheAwareRuntimeDataProvider(
            @Qualifier("machine-runtime-data-cache") Cache<MachineRuntime> cache,
            MachineRuntimeRepository runtimeRepository
    ) {
        this.cache = cache;
        this.runtimeRepository = runtimeRepository;
    }

    public List<MachineRuntime> findByParams(MachineMetricsQueryParams queryParams) {
        return findByParamsAndCache(queryParams, Optional.empty());
    }

    /*
        TODO: refactor this method
     */
    public long sumDowntimeInTimeframe(MachineMetricsQueryParams queryParams) {
        List<MachineRuntime> runtime = findByParams(queryParams);
        if (runtime.size() == 0) {
            return 0;
        }
        List<Long> downtime = new ArrayList<>();
        for (int i = 1; i < runtime.size(); i++) {
            MachineRuntime previousRecord = runtime.get(i - 1);
            MachineRuntime currentRecord = runtime.get(i);
            if (currentRecord.isRunning() && !previousRecord.isRunning()) {
                downtime.add(Duration.between(previousRecord.getDatetime(), currentRecord.getDatetime()).toMillis());
            }
        }
        return downtime.stream().mapToLong(e -> e).sum();
    }

    /*
        TODO: refactor this method
     */
    public long sumUptimeInTimeframe(MachineMetricsQueryParams queryParams) {
        List<MachineRuntime> runtime = findByParams(queryParams);
        if (runtime.size() == 0) {
            return 0;
        }
        List<Long> uptime = new ArrayList<>();
        for (int i = 1; i < runtime.size(); i++) {
            MachineRuntime previousRecord = runtime.get(i - 1);
            MachineRuntime currentRecord = runtime.get(i);
            if (!currentRecord.isRunning() && previousRecord.isRunning()) {
                uptime.add(Duration.between(previousRecord.getDatetime(), currentRecord.getDatetime()).toMillis());
            }
        }
        return uptime.stream().mapToLong(e -> e).sum();
    }

    private List<MachineRuntime> findByParamsAndCache(MachineMetricsQueryParams queryParams, Optional<Boolean> isRunning) {
        return cache.isInCache(queryParams) ? cache.getFromCache(queryParams)
                : cache.cacheAndReturn(runtimeRepository.findBy(
                queryParams.getMachineName(),
                queryParams.getParsedDateTimeFrom(),
                queryParams.getParsedDateTimeTo(),
                isRunning
        ));
    }
}
