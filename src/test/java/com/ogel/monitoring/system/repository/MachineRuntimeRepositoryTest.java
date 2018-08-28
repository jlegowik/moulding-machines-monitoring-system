package com.ogel.monitoring.system.repository;

import com.ogel.common.date.DateUtils;
import com.ogel.monitoring.system.config.repository.RepositoryTestConfiguration;
import com.ogel.monitoring.system.model.db.MachineRuntime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.*;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RepositoryTestConfiguration.class)
@DirtiesContext
@Sql(scripts = "/sql/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
public class MachineRuntimeRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MachineRuntimeRepository runtimeRepository;

    @Test
    public void shouldFindRequiredCountOfResultsWithProvidedParameters() {
        //given
        String machineName = "4x2 brick mould";
        OffsetDateTime from = DateUtils.fromMills(1514764800000L);
        OffsetDateTime to = DateUtils.fromMills(1514767200000L);

        //when
        List<MachineRuntime> production = runtimeRepository
                .findBy(machineName, from, to, Optional.empty());

        //then
        assertEquals(3, production.size());
    }
}
