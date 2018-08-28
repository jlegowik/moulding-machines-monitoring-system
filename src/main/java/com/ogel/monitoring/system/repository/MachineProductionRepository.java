package com.ogel.monitoring.system.repository;

import com.ogel.common.date.DateUtils;
import com.ogel.monitoring.system.model.MachineVariables;
import com.ogel.monitoring.system.model.db.MachineProduction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MachineProductionRepository {

    private final JdbcTemplate template;

    public MachineProductionRepository(JdbcTemplate template) {
        this.template = template;
    }

    public void insert(MachineProduction production) {
        template.update(
                "INSERT INTO Production ("
                        + "machine_name, variable_name, datetime_from, datetime_to, value) "
                        + "VALUES (?, ?, ?, ?, ?)",
                production.getMachineName(),
                production.getVariableName().getValue(),
                DateUtils.toTimestamp(production.getDatetimeFrom()),
                DateUtils.toTimestamp(production.getDatetimeTo()),
                production.getValue()
        );
    }

    public List<MachineProduction> findBy(
            String machineName,
            OffsetDateTime dateTimeFrom,
            OffsetDateTime dateTimeTo,
            Optional<MachineVariables> machineVariableName
    ) {
        return machineVariableName
                .map(machineVariables -> findBy(machineName, dateTimeFrom, dateTimeTo, machineVariables))
                .orElseGet(() -> findBy(machineName, dateTimeFrom, dateTimeTo));
    }

    private List<MachineProduction> findBy(
            String machineName,
            OffsetDateTime dateTimeFrom,
            OffsetDateTime dateTimeTo
    ) {
        return new ArrayList<>(template.query(
                "SELECT * FROM Production WHERE machine_name = ? " +
                        "AND datetime_from >= ? " +
                        "AND datetime_to <= ? " +
                        "ORDER BY datetime_from",
                new MachineProductionMapper(),
                machineName,
                DateUtils.toTimestamp(dateTimeFrom),
                DateUtils.toTimestamp(dateTimeTo)
        ));
    }

    private List<MachineProduction> findBy(
            String machineName,
            OffsetDateTime dateTimeFrom,
            OffsetDateTime dateTimeTo,
            MachineVariables machineVariableName
    ) {
        return new ArrayList<>(template.query(
                "SELECT * FROM Production WHERE machine_name = ? " +
                        "AND variable_name = ? " +
                        "AND datetime_from >= ? " +
                        "AND datetime_to <= ? " +
                        "ORDER BY datetime_from",
                new MachineProductionMapper(),
                machineName,
                machineVariableName.getValue(),
                DateUtils.toTimestamp(dateTimeFrom),
                DateUtils.toTimestamp(dateTimeTo)
        ));
    }
}
