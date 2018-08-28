package com.ogel.monitoring.system.repository;

import com.ogel.common.date.DateUtils;
import com.ogel.monitoring.system.model.MachineVariables;
import com.ogel.monitoring.system.model.db.MachineProduction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MachineProductionMapper implements RowMapper<MachineProduction> {

    @Override
    public MachineProduction mapRow(ResultSet rs, int rowNumber) throws SQLException {
        MachineProduction production = new MachineProduction();
        production.setId(rs.getInt(1));
        production.setMachineName(rs.getString(2));
        production.setVariableName(MachineVariables.of(rs.getString(3)));
        production.setDatetimeFrom(DateUtils.fromTimestamp(rs.getTimestamp(4)));
        production.setDatetimeTo(DateUtils.fromTimestamp(rs.getTimestamp(5)));
        production.setValue(rs.getDouble(6));

        return production;
    }
}
