package com.beaconfire.mapper;

import com.beaconfire.domain.jdbc.ClassToClassroomDisplay;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ClassToClassroomDisplayRowMapper implements RowMapper<ClassToClassroomDisplay> {

    @Override
    public ClassToClassroomDisplay mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ClassToClassroomDisplay.builder()
                .class_id(rs.getInt("class_id"))
                .name(rs.getString("name"))
                .building(rs.getString("building"))
                .capacity(rs.getInt("capacity"))
                .build();
    }

}

