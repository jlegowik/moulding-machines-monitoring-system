package com.ogel.monitoring.system.repository;

import com.ogel.common.date.DateUtils;
import com.ogel.monitoring.system.model.db.MachineRuntime;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MachineRuntimeMapper implements RowMapper<MachineRuntime> {

    @Override
    public MachineRuntime mapRow(ResultSet rs, int rowNumber) throws SQLException {
        MachineRuntime runtime = new MachineRuntime();
        runtime.setId(rs.getInt(1));
        runtime.setMachineName(rs.getString(2));
        runtime.setDatetime(DateUtils.fromTimestamp(rs.getTimestamp(3)));
        runtime.setRunning(BooleanUtils.toBoolean(rs.getInt(4)));

        return runtime;
    }
}
