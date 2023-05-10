package com.beaconfire.mapper;

import com.beaconfire.domain.jdbc.AdminApplication;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AdminApplicationRowMapper implements RowMapper<AdminApplication> {

    @Override
    public AdminApplication mapRow(ResultSet rs, int rowNum) throws SQLException {
        AdminApplication adminApplication = new AdminApplication();
        adminApplication.setFirst_name(rs.getString("first_name"));
        adminApplication.setLast_name(rs.getString("last_name"));
        adminApplication.setEmail(rs.getString("email"));
        adminApplication.setCourse_name(rs.getString("course_name"));
        adminApplication.setClass_id(rs.getInt("class_id"));
        adminApplication.setSemester_name(rs.getString("semester_name"));
        adminApplication.setRequest(rs.getString("request"));
        adminApplication.setCreation_time(rs.getTime("creation_time").toLocalTime());
        adminApplication.setStatus(rs.getString("status"));
        adminApplication.setStudent_id(rs.getInt("student_id"));
        adminApplication.setApplication_id(rs.getInt("application_id"));
        return adminApplication;
    }
}
