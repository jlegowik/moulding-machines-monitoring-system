package com.ogel.monitoring.system.repository;

import com.ogel.common.date.DateUtils;
import com.ogel.monitoring.system.model.db.MachineRuntime;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MachineRuntimeRepository {

    private final JdbcTemplate template;

    public MachineRuntimeRepository(JdbcTemplate template) {
        this.template = template;
    }

    public void insert(MachineRuntime runtime) {
        template.update(
                "INSERT INTO Runtime ("
                        + "machine_name, datetime, isrunning) "
                        + "VALUES (?, ?, ?)",
                runtime.getMachineName(),
                DateUtils.toTimestamp(runtime.getDatetime()),
                BooleanUtils.toInteger(runtime.isRunning())
        );
    }

    public List<MachineRuntime> findBy(
            String machineName,
            OffsetDateTime dateTimeFrom,
            OffsetDateTime dateTimeTo,
            Optional<Boolean> isRunning
    ) {
        return isRunning
                .map(e -> findBy(machineName, dateTimeFrom, dateTimeTo, isRunning))
                .orElseGet(() -> findBy(machineName, dateTimeFrom, dateTimeTo));
    }

    private List<MachineRuntime> findBy(
            String machineName,
            OffsetDateTime dateTimeFrom,
            OffsetDateTime dateTimeTo
    ) {
        return new ArrayList<>(template.query(
                "SELECT * FROM Runtime WHERE machine_name = ? " +
                        "AND datetime >= ? " +
                        "AND datetime <= ? " +
                        "ORDER BY datetime",
                new MachineRuntimeMapper(),
                machineName,
                DateUtils.toTimestamp(dateTimeFrom),
                DateUtils.toTimestamp(dateTimeTo)
        ));
    }

    private List<MachineRuntime> findBy(
            String machineName,
            OffsetDateTime dateTimeFrom,
            OffsetDateTime dateTimeTo,
            boolean isRunning
    ) {
        return new ArrayList<>(template.query(
                "SELECT * FROM Runtime WHERE machine_name = ? " +
                        "AND datetime >= ? " +
                        "AND datetime <= ? " +
                        "AND isrunning = ? " +
                        "ORDER BY datetime",
                new MachineRuntimeMapper(),
                machineName,
                DateUtils.toTimestamp(dateTimeFrom),
                DateUtils.toTimestamp(dateTimeTo),
                BooleanUtils.toInteger(isRunning)
        ));
    }
}
