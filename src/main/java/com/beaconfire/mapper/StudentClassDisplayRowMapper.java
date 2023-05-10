package com.beaconfire.mapper;

import com.beaconfire.domain.jdbc.StudentClassDisplay;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudentClassDisplayRowMapper implements RowMapper<StudentClassDisplay> {

    @Override
    public StudentClassDisplay mapRow(ResultSet rs, int rowNum) throws SQLException {
        return StudentClassDisplay.builder()
                .student_id(rs.getInt("student_id"))
                .class_id(rs.getInt("class_id"))
                .course_name(rs.getString("course_name"))
                .course_code(rs.getString("course_code"))
                .department_name(rs.getString("department_name"))
                .school_name(rs.getString("school_name"))
                .semester_name(rs.getString("semester_name"))
                .status(rs.getString("status"))
                .build();
    }
}
