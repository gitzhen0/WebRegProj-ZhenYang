package com.beaconfire.mapper;

import com.beaconfire.domain.jdbc.StudentApplicationDisplay;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudentApplicationDisplayRowMapper implements RowMapper<StudentApplicationDisplay> {

    @Override
    public StudentApplicationDisplay mapRow(ResultSet rs, int rowNum) throws SQLException {
        return StudentApplicationDisplay.builder()
                .application_id(rs.getInt("application_id"))
                .student_id(rs.getInt("student_id"))
                .class_id(rs.getInt("class_id"))
                .course_name(rs.getString("course_name"))
                .course_code(rs.getString("course_code"))
                .semester_name(rs.getString("semester_name"))
                .creation_time(rs.getTime("creation_time").toLocalTime())
                .request(rs.getString("request"))
                .feedback(rs.getString("feedback"))
                .status(rs.getString("status"))
                .build();
    }

}

