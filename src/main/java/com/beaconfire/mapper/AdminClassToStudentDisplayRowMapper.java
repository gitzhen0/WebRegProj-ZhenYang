package com.beaconfire.mapper;

import com.beaconfire.domain.jdbc.AdminClassToStudentDisplay;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class AdminClassToStudentDisplayRowMapper implements RowMapper<AdminClassToStudentDisplay> {

    @Override
    public AdminClassToStudentDisplay mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        return AdminClassToStudentDisplay.builder()
                .class_id(rs.getInt("class_id"))
                .student_id(rs.getInt("student_id"))
                .first_name(rs.getString("first_name"))
                .last_name(rs.getString("last_name"))
                .email(rs.getString("email"))
                .department_name(rs.getString("department_name"))
                .school_name(rs.getString("school_name"))
                .build();
    }
}
