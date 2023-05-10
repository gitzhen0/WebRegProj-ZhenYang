package com.beaconfire.mapper;

import com.beaconfire.domain.jdbc.Student;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudentRowMapper implements RowMapper<Student>{

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Student.builder()
                .id(rs.getInt("id"))
                .first_name(rs.getString("first_name"))
                .last_name(rs.getString("last_name"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .department_id(rs.getInt("department_id"))
                .is_active(rs.getInt("is_active"))
                .is_admin(rs.getInt("is_admin"))
                .build();
    }

}
