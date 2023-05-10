package com.beaconfire.dao.jdbc;

import com.beaconfire.dao.ClassToClassroomDisplayDao;
import com.beaconfire.domain.jdbc.ClassToClassroomDisplay;
import com.beaconfire.mapper.ClassToClassroomDisplayRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("classToClassroomDisplayDaoJdbcImpl")
public class ClassToClassroomDisplayDaoJdbcImpl implements ClassToClassroomDisplayDao {
    JdbcTemplate jdbcTemplate;

    ClassToClassroomDisplayRowMapper rowMapper;

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ClassToClassroomDisplayDaoJdbcImpl(JdbcTemplate jdbcTemplate, ClassToClassroomDisplayRowMapper rowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public ClassToClassroomDisplay getClassroomByClassId(int class_id) {
        String query = "SELECT cl.id AS class_id,\n" +
                "       cr.name AS name,\n" +
                "       cr.building AS building,\n" +
                "       cr.capacity AS capacity\n" +
                "FROM WebRegClass cl\n" +
                "JOIN Classroom cr ON cl.classroom_id = cr.id\n" +
                "WHERE cl.id = ?";
        List<ClassToClassroomDisplay> classToClassroomDisplays = jdbcTemplate.query(query, rowMapper, class_id);
        return classToClassroomDisplays.size() == 0 ? null : classToClassroomDisplays.get(0);
    }
}
