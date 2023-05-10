package com.beaconfire.mapper;

import com.beaconfire.domain.jdbc.AdminClassDisplay;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AdminClassDisplayRowMapper implements RowMapper<AdminClassDisplay> {

    @Override
    public AdminClassDisplay mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AdminClassDisplay.builder()
                .class_id(rs.getInt("class_id"))
                .course_id(rs.getInt("course_id"))
                .course_name(rs.getString("course_name"))
                .course_code(rs.getString("course_code"))
                .department_name(rs.getString("department_name"))
                .school_name(rs.getString("school_name"))
                .semester_name(rs.getString("semester_name"))
                .capacity(rs.getInt("capacity"))
                .enrollment_num(rs.getInt("enrollment_num"))
                .is_active(rs.getString("is_active"))
                .build();
    }
}
