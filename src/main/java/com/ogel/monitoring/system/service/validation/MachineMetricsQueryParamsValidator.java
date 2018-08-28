package com.ogel.monitoring.system.service.validation;

import com.ogel.common.error.RestValidationError;
import com.ogel.common.exception.ValidationFailedException;
import com.ogel.monitoring.system.model.query.MachineMetricsQueryParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MachineMetricsQueryParamsValidator {

    private static final Logger logger = LoggerFactory.getLogger(MachineMetricsQueryParamsValidator.class);

    private List<RestValidationError> errorList = new ArrayList<>();
    private List<Check<MachineMetricsQueryParams>> checks = new ArrayList<>();

    public void validate(MachineMetricsQueryParams queryParams) {
        errorList.clear();
        for (Check<MachineMetricsQueryParams> check : checks) {
            check.validate(queryParams, errorList);
        }
        if (errorList.size() > 0) {
            logger.info("Validation failed. '{}' parameters contains errors", errorList.size());
            throw new ValidationFailedException("Query params validation failed", errorList);
        }
    }

    public MachineMetricsQueryParamsValidator addValidator(Check<MachineMetricsQueryParams> check) {
        logger.info("Register field check '{}'", check.getClass().getSimpleName());
        checks.add(check);
        return this;
    }
}