package com.beaconfire.dao.jdbc;

import com.beaconfire.dao.ClassToSemesterDisplayDao;
import com.beaconfire.domain.jdbc.ClassToSemesterDisplay;
import com.beaconfire.mapper.ClassToSemesterDisplayRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("classToSemesterDisplayDaoJdbcImpl")
public class ClassToSemesterDisplayDaoJdbcImpl implements ClassToSemesterDisplayDao {
    JdbcTemplate jdbcTemplate;

    ClassToSemesterDisplayRowMapper rowMapper;

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ClassToSemesterDisplayDaoJdbcImpl(JdbcTemplate jdbcTemplate, ClassToSemesterDisplayRowMapper rowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public ClassToSemesterDisplay getSemesterbyClassId(int classId) {
        String query = "SELECT cl.id AS class_id,\n" +
                "       s.start_date AS start_date,\n" +
                "       s.end_date AS end_date,\n" +
                "       s.name AS semester_name\n" +
                "FROM WebRegClass cl\n" +
                "JOIN Semester s ON cl.semester_id = s.id\n" +
                "WHERE cl.id = ?";
        List<ClassToSemesterDisplay> classToSemesterDisplays = jdbcTemplate.query(query, rowMapper, classId);
        return classToSemesterDisplays.size() == 0 ? null : classToSemesterDisplays.get(0);
    }
}
