package com.beaconfire.dao.jdbc;

import com.beaconfire.dao.ClassToProfessorDisplayDao;
import com.beaconfire.domain.jdbc.ClassToProfessorDisplay;
import com.beaconfire.mapper.ClassToProfessorDisplayRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("classToProfessorDisplayDaoJdbcImpl")
public class ClassToProfessorDisplayDaoJdbcImpl implements ClassToProfessorDisplayDao {
    JdbcTemplate jdbcTemplate;

    ClassToProfessorDisplayRowMapper rowMapper;

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ClassToProfessorDisplayDaoJdbcImpl(JdbcTemplate jdbcTemplate, ClassToProfessorDisplayRowMapper rowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public ClassToProfessorDisplay getProfessorByClassId(int class_id) {
        String query = "SELECT cl.id AS class_id,\n" +
                "       p.first_name AS first_name,\n" +
                "       p.last_name AS last_name,\n" +
                "       p.email AS email,\n" +
                "       d.name AS department_name,\n" +
                "       d.school AS school_name\n" +
                "FROM WebRegClass cl\n" +
                "JOIN Professor p ON cl.professor_id = p.id\n" +
                "JOIN Department d ON p.department_id = d.id\n" +
                "WHERE cl.id = ?";
        List<ClassToProfessorDisplay> classToProfessorDisplays = jdbcTemplate.query(query, rowMapper, class_id);
        return classToProfessorDisplays.size() == 0 ? null : classToProfessorDisplays.get(0);
    }
}
