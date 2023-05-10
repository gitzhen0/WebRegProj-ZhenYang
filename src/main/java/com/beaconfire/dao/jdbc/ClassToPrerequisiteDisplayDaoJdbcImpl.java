package com.beaconfire.dao.jdbc;

import com.beaconfire.dao.ClassToPrerequisiteDisplayDao;
import com.beaconfire.domain.jdbc.ClassToPrerequisiteDisplay;
import com.beaconfire.mapper.ClassToPrerequisiteDisplayRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("classToPrerequisiteDisplayDaoJdbcImpl")
public class ClassToPrerequisiteDisplayDaoJdbcImpl implements ClassToPrerequisiteDisplayDao {
    JdbcTemplate jdbcTemplate;

    ClassToPrerequisiteDisplayRowMapper rowMapper;

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ClassToPrerequisiteDisplayDaoJdbcImpl(JdbcTemplate jdbcTemplate, ClassToPrerequisiteDisplayRowMapper rowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public ClassToPrerequisiteDisplay getPrerequisiteByClassId(int classId) {
        String query = "SELECT pre_course.name AS prerequisite_name,\n" +
                "cl.id AS class_id,\n" +
                "pr.pre_course_id AS prerequisite_id\n" +
                "FROM WebRegClass cl\n" +
                "JOIN Course c ON cl.course_id = c.id\n" +
                "JOIN Prerequisite pr ON c.id = pr.course_id\n" +
                "JOIN Course pre_course ON pr.pre_course_id = pre_course.id\n" +
                "WHERE cl.id = ?";
        List<ClassToPrerequisiteDisplay> classToPrerequisiteDisplays = jdbcTemplate.query(query, rowMapper, classId);
        return classToPrerequisiteDisplays.size() == 0 ? null : classToPrerequisiteDisplays.get(0);
    }
}
