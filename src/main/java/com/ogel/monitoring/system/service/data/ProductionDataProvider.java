package com.ogel.monitoring.system.service.data;

import com.ogel.monitoring.system.model.db.MachineProduction;
import com.ogel.monitoring.system.model.query.MachineMetricsQueryParams;

import java.util.List;

public interface ProductionDataProvider {

    List<MachineProduction> findByParams(MachineMetricsQueryParams queryParams);

    List<MachineProduction> findProductionByParams(MachineMetricsQueryParams queryParams);

    List<MachineProduction> findScrapByParams(MachineMetricsQueryParams queryParams);

    List<MachineProduction> findTemperatureByParams(MachineMetricsQueryParams queryParams);

    int sumProductionInTimeframe(MachineMetricsQueryParams queryParams);

    int sumScrapInTimeframe(MachineMetricsQueryParams queryParams);
}
