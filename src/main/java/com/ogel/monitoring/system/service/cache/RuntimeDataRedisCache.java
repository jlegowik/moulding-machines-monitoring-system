package com.ogel.monitoring.system.service.cache;

import com.ogel.monitoring.system.model.db.MachineRuntime;
import com.ogel.monitoring.system.model.query.MachineMetricsQueryParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component("machine-runtime-data-cache")
public class RuntimeDataRedisCache implements Cache<MachineRuntime> {

    private final static Logger LOGGER = LoggerFactory.getLogger(RuntimeDataRedisCache.class);

    private StringRedisTemplate redisTemplate;

    public RuntimeDataRedisCache(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean isInCache(MachineMetricsQueryParams queryParams) {
        /*
            Logic to check if data is in cache
         */
        return false;
    }

    @Override
    public List<MachineRuntime> getFromCache(MachineMetricsQueryParams queryParams) {
        /*
            Logic to get data from cache
         */
        return Collections.emptyList();
    }

    @Override
    public List<MachineRuntime> cacheAndReturn(List<MachineRuntime> data) {
        LOGGER.info("Cache machine runtime data");
        /*
            Logic to cache data
         */
        return data;
    }
}
