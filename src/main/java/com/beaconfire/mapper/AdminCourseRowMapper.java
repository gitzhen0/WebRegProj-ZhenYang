package com.beaconfire.mapper;

import com.beaconfire.domain.jdbc.AdminCourse;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AdminCourseRowMapper implements RowMapper<AdminCourse> {
    @Override
    public AdminCourse mapRow(ResultSet resultSet, int i) throws SQLException {
        AdminCourse adminCourse = new AdminCourse();
        adminCourse.setId(resultSet.getInt("id"));
        adminCourse.setName(resultSet.getString("name"));
        return adminCourse;
    }
}
