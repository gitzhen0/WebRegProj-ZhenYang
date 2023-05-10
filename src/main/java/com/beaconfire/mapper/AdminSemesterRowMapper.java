package com.beaconfire.mapper;

import com.beaconfire.domain.jdbc.AdminSemester;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AdminSemesterRowMapper implements RowMapper<AdminSemester> {

    public AdminSemester mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AdminSemester.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build();
    }
}
