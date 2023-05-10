package com.beaconfire.mapper;

import com.beaconfire.domain.jdbc.ClassToSemesterDisplay;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ClassToSemesterDisplayRowMapper implements RowMapper<ClassToSemesterDisplay> {

    @Override
    public ClassToSemesterDisplay mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ClassToSemesterDisplay.builder()
                .class_id(rs.getInt("class_id"))
                .start_date(rs.getDate("start_date").toLocalDate())
                .end_date(rs.getDate("end_date").toLocalDate())
                .semester_name(rs.getString("semester_name"))
                .build();
    }

}

