package com.beaconfire.mapper;

import com.beaconfire.domain.jdbc.AdminHomeDisplay;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AdminHomeDisplayRowMapper implements RowMapper<AdminHomeDisplay> {

    @Override
    public AdminHomeDisplay mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AdminHomeDisplay.builder()
                .student_id(rs.getInt("student_id"))
                .first_name(rs.getString("first_name"))
                .last_name(rs.getString("last_name"))
                .email(rs.getString("email"))
                .department_name(rs.getString("department_name"))
                .school_name(rs.getString("school_name"))
                .is_active(rs.getString("is_active"))
                .build();
    }
}

