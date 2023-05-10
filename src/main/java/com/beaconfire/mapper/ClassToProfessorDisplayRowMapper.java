package com.beaconfire.mapper;

import com.beaconfire.domain.jdbc.ClassToProfessorDisplay;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ClassToProfessorDisplayRowMapper implements RowMapper<ClassToProfessorDisplay> {

    @Override
    public ClassToProfessorDisplay mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ClassToProfessorDisplay.builder()
                .class_id(rs.getInt("class_id"))
                .first_name(rs.getString("first_name"))
                .last_name(rs.getString("last_name"))
                .email(rs.getString("email"))
                .department_name(rs.getString("department_name"))
                .school_name(rs.getString("school_name"))
                .build();
    }

}

