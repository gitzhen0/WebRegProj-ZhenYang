package com.beaconfire.mapper;

import com.beaconfire.domain.jdbc.ClassManagementDisplay;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class ClassManagementDisplayRowMapper implements RowMapper<ClassManagementDisplay> {


    @Override
    public ClassManagementDisplay mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ClassManagementDisplay.builder()
                .class_id(rs.getInt("class_id"))
                .course_name(rs.getString("course_name"))
                .course_code(rs.getString("course_code"))
                .department_name(rs.getString("department_name"))
                .school_name(rs.getString("school_name"))
                .enrollment_num(rs.getString("enrollment_num"))
                .capacity(rs.getString("capacity"))
                .semester_name(rs.getString("semester_name"))
                .isEnrolled(rs.getString("isEnrolled"))
                .build();
    }
}
