package com.ogel.monitoring.system;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = "/sql/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ApplicationE2eTest {

    private final static String PATH_PATTERN = "http://localhost:%s/moulding-machines-monitoring-system/v1/metrics/machine";

    @LocalServerPort
    private int port;

    private String baseUrl;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        this.baseUrl = String.format(PATH_PATTERN, port);
    }

    @Test
    public void shouldReturnRequestParamsValidationErrors() {
        String path = baseUrl + "?machineName={machineName}&from={from}&to={to}";

        //given
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("machineName", "3x2 brick mould");
        queryParams.put("from", 1514764800000L);
        queryParams.put("to", 1514767200000L);

        // 1) Invalid 'machineName' param
        //when
        ResponseEntity<String> invalidMachineNameParamError = restTemplate.getForEntity(
                baseUrl + "?from={from}&to={to}", String.class, queryParams);

        //then
        assertThat(invalidMachineNameParamError.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(invalidMachineNameParamError.getBody())
                .isEqualTo("{\"code\":\"VALIDATION_FAILED_ERROR\"," +
                        "\"message\":\"Request parameters validation failed\"," +
                        "\"errors\":[{\"field\":\"machineName\",\"message\":\"Required String parameter 'machineName' is not present\"}]}");

        // 2) Invalid 'from' param
        //when
        ResponseEntity<String> invalidFromParamError = restTemplate.getForEntity(
                baseUrl + "?machineName={machineName}&to={to}", String.class, queryParams);

        //then
        assertThat(invalidFromParamError.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(invalidFromParamError.getBody())
                .isEqualTo("{\"code\":\"VALIDATION_FAILED_ERROR\"," +
                        "\"message\":\"Request parameters validation failed\"," +
                        "\"errors\":[{\"field\":\"from\",\"message\":\"Required Long parameter 'from' is not present\"}]}");

        // 3) Invalid 'to' param
        //when
        ResponseEntity<String> invalidToParamError = restTemplate.getForEntity(
                baseUrl + "?machineName={machineName}&from={from}", String.class, queryParams);

        //then
        assertThat(invalidToParamError.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(invalidToParamError.getBody())
                .isEqualTo("{\"code\":\"VALIDATION_FAILED_ERROR\"," +
                        "\"message\":\"Request parameters validation failed\"," +
                        "\"errors\":[{\"field\":\"to\",\"message\":\"Required Long parameter 'to' is not present\"}]}");
    }

    @Test
    public void shouldGoThroughWholeProcessOfMetricsCalculationSuccessfully() throws IOException {
        //given
        String path = baseUrl + "?machineName={machineName}&from={from}&to={to}";
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("machineName", "3x2 brick mould");
        queryParams.put("from", 1514764800000L);
        queryParams.put("to", 1514767200000L);

        //when
        ResponseEntity<String> getMachineMetrics = restTemplate.getForEntity(
                path, String.class, queryParams);

        //then
        assertThat(getMachineMetrics.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals("{\"machineName\":\"3x2 brick mould\",\"datetimeFrom\":1514764800000,\"datetimeTo\":1514767200000,\"production\":14643,\"scrapPercentage\":0.031675704271921706,\"downtimePercentage\":0.5,\"machineStatus\":\"GOOD\",\"performance\":0.7561,\"availability\":0.16666666666666666,\"quality\":0.9683242957280783,\"oee\":0.12202500000000001,\"productionInTime\":{\"2018-01-01\":[{\"hour\":0,\"value\":14643}]}}", getMachineMetrics.getBody());
    }

}
