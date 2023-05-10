package com.beaconfire.dao.jdbc;

import com.beaconfire.dao.StudentApplicationDisplayDao;
import com.beaconfire.domain.jdbc.StudentApplicationDisplay;
import com.beaconfire.mapper.StudentApplicationDisplayRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository("studentApplicationDisplayDaoJdbcImpl")
public class StudentApplicationDisplayDaoJdbcImpl implements StudentApplicationDisplayDao {
    JdbcTemplate jdbcTemplate;

    StudentApplicationDisplayRowMapper rowMapper;

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public StudentApplicationDisplayDaoJdbcImpl(JdbcTemplate jdbcTemplate, StudentApplicationDisplayRowMapper rowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<StudentApplicationDisplay> getApplicationsByStudentId(int student_id){
        String query = "SELECT\n" +
                "    a.id AS application_id,\n" +
                "    a.student_id,\n" +
                "    a.class_id,\n" +
                "    c.name AS course_name,\n" +
                "    c.code AS course_code,\n" +
                "    s.name AS semester_name,\n" +
                "    a.creation_time,\n" +
                "    a.request,\n" +
                "    a.status,\n" +
                "    a.feedback\n" +
                "FROM\n" +
                "    Application a\n" +
                "JOIN\n" +
                "    WebRegClass cl ON a.class_id = cl.id\n" +
                "JOIN\n" +
                "    Course c ON cl.course_id = c.id\n" +
                "JOIN\n" +
                "    Semester s ON cl.semester_id = s.id\n" +
                "WHERE\n" +
                "    a.student_id = ?";
        List<StudentApplicationDisplay> studentApplicationDisplays = jdbcTemplate.query(query, rowMapper, student_id);
        return studentApplicationDisplays.size() == 0 ? null : studentApplicationDisplays;
    }

    public void addNewApplication(int studentId, int classId, String request) {
        // Check if the combination of student_id, class_id, and request is unique
        String checkQuery = "SELECT COUNT(*) FROM Application WHERE student_id = ? AND class_id = ? AND request = ?";
        int count = jdbcTemplate.queryForObject(checkQuery, Integer.class, studentId, classId, request);

        // If the combination is unique, insert the new application
        if (count == 0) {
            String query = "INSERT INTO Application (student_id, class_id, creation_time, request, status) VALUES (?, ?, ?, ?, 'pending')";
            jdbcTemplate.update(query, preparedStatement -> {
                preparedStatement.setInt(1, studentId);
                preparedStatement.setInt(2, classId);
                preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setString(4, request);
            });
        } else {
//            System.out.println("Combination of student_id, class_id, and request already exists in the Application table.");
        }
    }


    public void removeApplication(int studentId, int classId) {
        String query = "DELETE FROM Application WHERE student_id = ? AND class_id = ?";
        jdbcTemplate.update(query, preparedStatement -> {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, classId);
        });
    }

    public void removeApplicationById(int applicationId) {
        String query = "DELETE FROM Application WHERE id = ?";
        jdbcTemplate.update(query, preparedStatement -> {
            preparedStatement.setInt(1, applicationId);
        });
    }

    public void removeStudentFromClass(int studentId, int classId) {
        // Remove the student from the class by deleting the corresponding record in the StudentClass table
        String queryRemoveStudentFromClass = "DELETE FROM StudentClass WHERE student_id = ? AND class_id = ? AND status = 'ongoing'";
        jdbcTemplate.update(queryRemoveStudentFromClass, studentId, classId);

        // Decrement the enrollment number for the class
        String queryDecrementEnrollment = "UPDATE WebRegClass SET enrollment_num = enrollment_num - 1 WHERE id = ?";
        jdbcTemplate.update(queryDecrementEnrollment, classId);
    }
}
