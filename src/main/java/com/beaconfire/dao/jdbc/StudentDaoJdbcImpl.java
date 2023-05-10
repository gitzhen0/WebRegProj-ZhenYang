package com.beaconfire.dao.jdbc;

import com.beaconfire.dao.StudentDao;
import com.beaconfire.domain.jdbc.Student;
import com.beaconfire.mapper.StudentRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository("studentDaoJdbcImpl")
public class StudentDaoJdbcImpl implements StudentDao {
    JdbcTemplate jdbcTemplate;

    StudentRowMapper rowMapper;

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public StudentDaoJdbcImpl(JdbcTemplate jdbcTemplate, StudentRowMapper rowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

//    public void registerStudent(String first_name, String last_name, String email, String password, int department_id, int is_active, int is_admin){
//        String query = "INSERT INTO Student (first_name, last_name, email, password, department_id, is_active, is_admin) values(?, ?, ?, ?, ?, ?, ?)";
//        Student stp = jdbcTemplate.update(query, first_name, last_name, email, password, department_id, is_active, is_admin);
//    }

    public int registerStudent(String first_name, String last_name, String email, String password, int department_id, int is_active, int is_admin) {
        String query = "INSERT INTO Student (first_name, last_name, email, password, department_id, is_active, is_admin) values (?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, first_name);
                    ps.setString(2, last_name);
                    ps.setString(3, email);
                    ps.setString(4, password);
                    ps.setInt(5, department_id);
                    ps.setInt(6, is_active);
                    ps.setInt(7, is_admin);
                    return ps;
                }, keyHolder);

        if (rowsAffected == 1) {
            return keyHolder.getKey().intValue();
        } else {
            throw new RuntimeException("Error registering student");
        }
    }

    public Student getStudentByEmail(String email){
        String query = "SELECT * FROM Student WHERE email = ?";
        Student student = jdbcTemplate.queryForObject(query, rowMapper, email);
        return student;
    }



}
