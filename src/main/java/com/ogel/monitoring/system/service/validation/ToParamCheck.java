package com.ogel.monitoring.system.service.validation;

import com.ogel.common.error.RestValidationError;
import com.ogel.monitoring.system.model.query.MachineMetricsQueryParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ToParamCheck implements Check<MachineMetricsQueryParams> {

    private static final Logger logger = LoggerFactory.getLogger(ToParamCheck.class);

    @Override
    public void validate(MachineMetricsQueryParams queryParams, List<RestValidationError> errors) {
        logger.info("'{}' param validation: '{}'", MachineMetricsQueryParams.Params.TO.fieldName(), queryParams.getMachineName());
        if (!isValid(queryParams.getDateTimeFrom())) {
            errors.add(createRestValidationError(MachineMetricsQueryParams.Params.TO.fieldName(), "Invalid value of timestamp"));
        }
    }

    private boolean isValid(Long timestamp) {
        // 'to' field validation logic
        return true;
    }
}