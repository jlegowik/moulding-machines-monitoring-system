package com.ogel.monitoring.system.service.data;

import com.ogel.monitoring.system.model.db.MachineRuntime;
import com.ogel.monitoring.system.model.query.MachineMetricsQueryParams;

import java.util.List;

public interface RuntimeDataProvider {

    List<MachineRuntime> findByParams(MachineMetricsQueryParams queryParams);

    long sumDowntimeInTimeframe(MachineMetricsQueryParams queryParams);

    long sumUptimeInTimeframe(MachineMetricsQueryParams queryParams);
}
