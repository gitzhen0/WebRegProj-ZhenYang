package com.beaconfire.mapper;


import com.beaconfire.domain.jdbc.StudentClass;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class StudentClassRowMapper implements RowMapper<StudentClass> {

        @Override
        public StudentClass mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {

            return StudentClass.builder()
                    .id(rs.getInt("id"))
                    .student_id(rs.getInt("student_id"))
                    .class_id(rs.getInt("class_id"))
                    .status(rs.getString("status"))
                    .build();
        }
}
