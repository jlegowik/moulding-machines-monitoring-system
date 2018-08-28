package com.ogel.monitoring.system.config;

import com.ogel.monitoring.system.metrics.*;
import com.ogel.monitoring.system.service.data.ProductionDataProvider;
import com.ogel.monitoring.system.service.data.RuntimeDataProvider;
import com.ogel.monitoring.system.service.validation.MachineMetricsQueryParamsValidator;
import com.ogel.monitoring.system.service.validation.FromParamCheck;
import com.ogel.monitoring.system.service.validation.MachineNameParamCheck;
import com.ogel.monitoring.system.service.validation.ToParamCheck;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MouldingMachinesMonitoringSystemProperties.class)
public class RootConfiguration {

    @Bean
    public MachineMetricsQueryParamsValidator machineMetricsQueryParamsValidator() {
        return new MachineMetricsQueryParamsValidator()
                .addValidator(new FromParamCheck())
                .addValidator(new ToParamCheck())
                .addValidator(new MachineNameParamCheck());
    }

    @Bean
    public MetricsCalculationsChain metricsCalculationsChain(
            MouldingMachinesMonitoringSystemProperties properties,
            RuntimeDataProvider runtimeDataProvider,
            ProductionDataProvider productionDataProvider
    ) {
        return new MetricsCalculationsChain()
                .registerStrategy(new DowntimePercentageCalculationStrategy(runtimeDataProvider))
                .registerStrategy(new TotalNetProductionCalculationStrategy(productionDataProvider))
                .registerStrategy(new ScrapPercentageCalculationStrategy(productionDataProvider))
                .registerStrategy(new TotalNetProductionInTimeCalculationStrategy(productionDataProvider))
                .registerStrategy(new TemperatureBasedMachineStateCalculationStrategy(productionDataProvider))
                .registerStrategy(new OverallEquipmentEfficiencyCalculationStrategy(properties, runtimeDataProvider, productionDataProvider));
    }
}
