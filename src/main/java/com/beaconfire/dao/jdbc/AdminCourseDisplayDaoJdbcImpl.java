package com.beaconfire.dao.jdbc;

import com.beaconfire.dao.AdminCourseDisplayDao;
import com.beaconfire.domain.jdbc.AdminClassroom;
import com.beaconfire.domain.jdbc.AdminCourseDisplay;
import com.beaconfire.domain.jdbc.AdminProfessor;
import com.beaconfire.domain.jdbc.AdminSemester;
import com.beaconfire.mapper.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("adminCourseDisplayDaoJdbcImpl")
public class AdminCourseDisplayDaoJdbcImpl implements AdminCourseDisplayDao {

    JdbcTemplate jdbcTemplate;

    AdminCourseDisplayRowMapper ACDrowMapper;

    AdminSemesterRowMapper ASrowMapper;

    AdminClassroomRowMapper ACrowMapper;

    AdminProfessorRowMapper AProwMapper;


    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AdminCourseDisplayDaoJdbcImpl(JdbcTemplate jdbcTemplate, AdminCourseDisplayRowMapper ACDrowMapper, AdminSemesterRowMapper ASrowMapper, AdminClassroomRowMapper ACrowMapper, AdminProfessorRowMapper AProwMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.ACDrowMapper = ACDrowMapper;
        this.ASrowMapper = ASrowMapper;
        this.ACrowMapper = ACrowMapper;
        this.AProwMapper = AProwMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    public List<AdminCourseDisplay> getAllCourses() {
        String query = "SELECT\n" +
                "    co.id AS course_id,\n" +
                "    co.name AS course_name,\n" +
                "    co.code AS course_code,\n" +
                "    d.name AS department_name,\n" +
                "    d.school AS school_name,\n" +
                "    co.description AS description\n" +
                "FROM\n" +
                "    Course co\n" +
                "JOIN\n" +
                "    Department d ON co.department_id = d.id";
        List<AdminCourseDisplay> adminCourseDisplays = jdbcTemplate.query(query, ACDrowMapper);
        return adminCourseDisplays.size() == 0 ? null : adminCourseDisplays;
    }

    public List<AdminProfessor> getAllProfessors(){
        String query = "SELECT\n" +
                "    p.id AS id,\n" +
                "    p.first_name AS first_name,\n" +
                "    p.last_name AS last_name\n" +
                "FROM\n" +
                "    Professor p";
        List<AdminProfessor> adminProfessors = jdbcTemplate.query(query, AProwMapper);
        return adminProfessors.size() == 0 ? null : adminProfessors;
    }

    public List<AdminClassroom> getAllClassrooms(){
        String query = "SELECT\n" +
                "    c.id AS id,\n" +
                "    c.name AS name\n" +
                "FROM\n" +
                "    Classroom c";
        List<AdminClassroom> adminClassrooms = jdbcTemplate.query(query, ACrowMapper);
        return adminClassrooms.size() == 0 ? null : adminClassrooms;
    }

    public List<AdminSemester> getAllSemesters(){
        String query = "SELECT\n" +
                "    s.id AS id,\n" +
                "    s.name AS name\n" +
                "FROM\n" +
                "    Semester s";
        List<AdminSemester> adminSemesters = jdbcTemplate.query(query, ASrowMapper);
        return adminSemesters.size() == 0 ? null : adminSemesters;
    }

    public void addNewCourse(String courseName, String courseCode, int departmentId, String description) {
        String query = "INSERT INTO Course (name, code, department_id, description) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(query, preparedStatement -> {
            preparedStatement.setString(1, courseName);
            preparedStatement.setString(2, courseCode);
            preparedStatement.setInt(3, departmentId);
            preparedStatement.setString(4, description);
        });
    }




}
