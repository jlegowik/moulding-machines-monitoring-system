package com.ogel.monitoring.system.service.cache;

import com.ogel.monitoring.system.model.db.MachineProduction;
import com.ogel.monitoring.system.model.query.MachineMetricsQueryParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component("machine-production-data-cache")
public class ProductionDataRedisCache implements Cache<MachineProduction> {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProductionDataRedisCache.class);

    private RedisTemplate redisTemplate;

    public ProductionDataRedisCache(RedisTemplate redisTemplate) {
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
    public List<MachineProduction> getFromCache(MachineMetricsQueryParams queryParams) {
        /*
            Logic to get data from cache
         */
        return Collections.emptyList();
    }

    @Override
    public List<MachineProduction> cacheAndReturn(List<MachineProduction> data) {
        LOGGER.info("Cache machine production data");
        /*
            Logic to cache data
         */
        return data;
    }
}
