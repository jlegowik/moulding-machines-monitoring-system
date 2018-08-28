package com.ogel.monitoring.system.service.cache;

import com.ogel.monitoring.system.model.query.MachineMetricsQueryParams;

import java.util.List;

public interface Cache<T> {

    boolean isInCache(MachineMetricsQueryParams queryParams);

    List<T> getFromCache(MachineMetricsQueryParams queryParams);

    List<T> cacheAndReturn(List<T> data);

}
