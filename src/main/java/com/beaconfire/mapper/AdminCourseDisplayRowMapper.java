package com.beaconfire.mapper;

import com.beaconfire.domain.jdbc.AdminCourseDisplay;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class AdminCourseDisplayRowMapper implements RowMapper<AdminCourseDisplay> {

        @Override
        public AdminCourseDisplay mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
            return AdminCourseDisplay.builder()
                    .course_id(rs.getInt("course_id"))
                    .course_name(rs.getString("course_name"))
                    .course_code(rs.getString("course_code"))
                    .department_name(rs.getString("department_name"))
                    .school_name(rs.getString("school_name"))
                    .description(rs.getString("description"))
                    .build();
        }
}
