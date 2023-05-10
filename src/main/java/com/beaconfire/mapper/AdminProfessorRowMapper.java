package com.beaconfire.mapper;

import com.beaconfire.domain.jdbc.AdminProfessor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AdminProfessorRowMapper implements RowMapper<AdminProfessor> {

    @Override
    public AdminProfessor mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AdminProfessor.builder()
                .id(rs.getInt("id"))
                .first_name(rs.getString("first_name"))
                .last_name(rs.getString("last_name"))
                .build();
    }
}
