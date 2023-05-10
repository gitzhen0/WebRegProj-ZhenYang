package com.beaconfire.mapper;

import com.beaconfire.domain.jdbc.ClassToPrerequisiteDisplay;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ClassToPrerequisiteDisplayRowMapper implements RowMapper<ClassToPrerequisiteDisplay> {

    @Override
    public ClassToPrerequisiteDisplay mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ClassToPrerequisiteDisplay.builder()
                .class_id(rs.getInt("class_id"))
                .prerequisite_id(rs.getInt("prerequisite_id"))
                .prerequisite_name(rs.getString("prerequisite_name"))
                .build();
    }

}

