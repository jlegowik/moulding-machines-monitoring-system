package com.ogel.monitoring.system.controller.v1;

import com.ogel.common.error.RestError;
import com.ogel.common.rest.versioning.ApiVersion;
import com.ogel.monitoring.system.model.dao.MachineMetricsDao;
import com.ogel.monitoring.system.model.query.MachineMetricsQueryParams;
import com.ogel.monitoring.system.service.calculation.MachineMetricsCalculator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/metrics", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@ApiVersion("1")
public class MachineMetricsController {

    private MachineMetricsCalculator machineMetricsCalculator;

    public MachineMetricsController(MachineMetricsCalculator machineMetricsCalculator) {
        this.machineMetricsCalculator = machineMetricsCalculator;
    }

    @RequestMapping(value = "/machine", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("Get metrics for machine")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request", response = RestError.class)
    })
    public MachineMetricsDao getMetricsForMachine(
            @RequestParam @ApiParam(value = "Machine name", required = true) String machineName,
            @RequestParam @ApiParam(value = "Timestamp in milliseconds. Moment in time 'from' which metrics should be calculated", required = true) Long from,
            @RequestParam @ApiParam(value = "Timestamp in milliseconds. Moment in time 'to' which metrics should be calculated", required = true) Long to
    ) {
        return machineMetricsCalculator.calculate(
                MachineMetricsQueryParams.newBuilder()
                        .machineName(machineName)
                        .dateTimeFrom(from)
                        .dateTimeTo(to)
                        .build()
        );
    }

}