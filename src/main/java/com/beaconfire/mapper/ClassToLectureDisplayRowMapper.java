package com.beaconfire.mapper;

import com.beaconfire.domain.jdbc.ClassToLectureDisplay;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ClassToLectureDisplayRowMapper implements RowMapper<ClassToLectureDisplay> {

    @Override
    public ClassToLectureDisplay mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ClassToLectureDisplay.builder()
                .class_id(rs.getInt("class_id"))
                .start_time(rs.getTime("start_time").toLocalTime())
                .end_time(rs.getTime("end_time").toLocalTime())
                .day_of_the_week(rs.getString("day_of_the_week"))
                .build();
    }
}
