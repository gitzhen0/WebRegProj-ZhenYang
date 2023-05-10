package com.beaconfire.mapper;

import com.beaconfire.domain.jdbc.AdminClassroom;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AdminClassroomRowMapper implements RowMapper<AdminClassroom> {

    @Override
    public AdminClassroom mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AdminClassroom.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build();
    }
}
