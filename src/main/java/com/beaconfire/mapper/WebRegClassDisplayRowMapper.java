package com.beaconfire.mapper;

import com.beaconfire.domain.jdbc.WebRegClassDisplay;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class WebRegClassDisplayRowMapper implements RowMapper<WebRegClassDisplay> {

        @Override
        public WebRegClassDisplay mapRow(ResultSet rs, int rowNum) throws SQLException {
            return WebRegClassDisplay.builder()
                    .class_id(rs.getString("class_id"))
                    .course_name(rs.getString("course_name"))
                    .course_code(rs.getString("course_code"))
                    .department_name(rs.getString("department_name"))
                    .school_name(rs.getString("school_name"))
                    .course_description(rs.getString("course_description"))
                    .capacity(rs.getInt("capacity"))
                    .enrollment_num(rs.getInt("enrollment_num"))
                    .is_active(rs.getString("is_active"))
                    .build();
        }

}
