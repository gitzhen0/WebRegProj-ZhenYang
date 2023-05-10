package com.beaconfire.dao.jdbc;

import com.beaconfire.domain.jdbc.ClassToLectureDisplay;
import com.beaconfire.mapper.ClassToLectureDisplayRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("classToLectureDisplayDaoJdbcImpl")
public class ClassToLectureDisplayDaoJdbcImpl implements com.beaconfire.dao.ClassToLectureDisplayDao{
    JdbcTemplate jdbcTemplate;

    ClassToLectureDisplayRowMapper rowMapper;

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ClassToLectureDisplayDaoJdbcImpl(JdbcTemplate jdbcTemplate, ClassToLectureDisplayRowMapper rowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public ClassToLectureDisplay getLectureByClassId(int classId) {
        String query = "SELECT l.class_id AS class_id,\n" +
                "       l.day_of_the_week AS day_of_the_week,\n" +
                "       l.start_time AS start_time,\n" +
                "       l.end_time AS end_time\n" +
                "FROM Lecture l\n" +
                "WHERE l.class_id = ?";
        return jdbcTemplate.query(query, rowMapper, classId).get(0);
    }


}
