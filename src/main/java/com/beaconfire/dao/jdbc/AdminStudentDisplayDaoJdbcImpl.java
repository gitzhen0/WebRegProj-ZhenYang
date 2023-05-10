package com.beaconfire.dao.jdbc;

import com.beaconfire.dao.AdminStudentDisplayDao;
import com.beaconfire.domain.jdbc.AdminHomeDisplay;
import com.beaconfire.domain.jdbc.StudentClassDisplay;
import com.beaconfire.mapper.AdminHomeDisplayRowMapper;
import com.beaconfire.mapper.StudentClassDisplayRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("adminStudentDisplayDaoJdbcImpl")
public class AdminStudentDisplayDaoJdbcImpl implements AdminStudentDisplayDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    AdminHomeDisplayRowMapper AHDrowMapper;

    @Autowired
    StudentClassDisplayRowMapper SCDrowMapper;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AdminHomeDisplay getStudent(int studentId) {
        String sql = "SELECT st.id AS student_id, st.first_name, st.last_name, st.email, d.name AS department_name, d.school AS school_name, st.is_active " +
                "FROM Student st " +
                "JOIN Department d ON st.department_id = d.id " +
                "WHERE st.is_admin = 0 AND st.id = :studentId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("studentId", studentId);
        return namedParameterJdbcTemplate.queryForObject(sql, params, AHDrowMapper);
    }


    public String UpdateStudentStatus(int studentId, int status) {
        String sql = "UPDATE Student SET is_active = :status WHERE id = :studentId";
        Map<String, Object> params = new HashMap<>();
        params.put("studentId", studentId);
        params.put("status", status);
        namedParameterJdbcTemplate.update(sql, params);
        return "success";
    }

    public List<StudentClassDisplay> getStudentClassesByStudentId(int studentId){
        String query = "SELECT\n" +
                "    s.id AS student_id,\n" +
                "    c.id AS class_id,\n" +
                "    co.name AS course_name,\n" +
                "    co.code AS course_code,\n" +
                "    d.name AS department_name,\n" +
                "    d.school AS school_name,\n" +
                "    sm.name AS semester_name,\n" +
                "    sc.status AS status\n" +
                "FROM\n" +
                "    Student s\n" +
                "JOIN\n" +
                "    StudentClass sc ON s.id = sc.student_id\n" +
                "JOIN\n" +
                "    WebRegClass c ON sc.class_id = c.id\n" +
                "JOIN\n" +
                "    Course co ON c.course_id = co.id\n" +
                "JOIN\n" +
                "    Department d ON co.department_id = d.id\n" +
                "JOIN\n" +
                "    Semester sm ON c.semester_id = sm.id\n" +
                "WHERE\n" +
                "    s.id = ?";
        List<StudentClassDisplay> studentClassDisplays = jdbcTemplate.query(query, SCDrowMapper, studentId);
        return studentClassDisplays.size() == 0 ? null : studentClassDisplays;
    }


    public void flipStudentStatus(int studentId) {
        // Get the current status of the student
        String getStatusQuery = "SELECT is_active FROM Student WHERE id = ?";
        boolean currentStatus = jdbcTemplate.queryForObject(getStatusQuery, Boolean.class, studentId);

        // Flip the status
        boolean newStatus = !currentStatus;

        // Update the student's status in the database
        String updateStatusQuery = "UPDATE Student SET is_active = ? WHERE id = ?";
        jdbcTemplate.update(updateStatusQuery, newStatus, studentId);
    }


    public void changeStudentClassStatus(int studentId, int classId, String status) {
        // Update the status of the student for the given class in the StudentClass table
        String updateStatusQuery = "UPDATE StudentClass SET status = ? WHERE student_id = ? AND class_id = ?";
        jdbcTemplate.update(updateStatusQuery, status, studentId, classId);
    }

}
