package com.ogel.monitoring.system.service.validation;

import com.google.common.base.Strings;
import com.ogel.common.error.RestValidationError;
import com.ogel.monitoring.system.model.query.MachineMetricsQueryParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MachineNameParamCheck implements Check<MachineMetricsQueryParams> {

    private static final Logger logger = LoggerFactory.getLogger(MachineNameParamCheck.class);

    @Override
    public void validate(MachineMetricsQueryParams queryParams, List<RestValidationError> errors) {
        logger.info("'{}' param validation: '{}'", MachineMetricsQueryParams.Params.MACHINE_NAME.fieldName(), queryParams.getMachineName());
        if (Strings.isNullOrEmpty(queryParams.getMachineName())) {
            errors.add(createRestValidationError(MachineMetricsQueryParams.Params.MACHINE_NAME.fieldName(), "Can't be null or empty string"));
        }
    }
}