package com.beaconfire.dao.jdbc;

import com.beaconfire.dao.AdminApplicationDao;
import com.beaconfire.domain.jdbc.AdminApplication;
import com.beaconfire.mapper.AdminApplicationRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("adminApplicationDaoJdbcImpl")
public class AdminApplicationDaoJdbcImpl implements AdminApplicationDao {
    JdbcTemplate jdbcTemplate;

    AdminApplicationRowMapper rowMapper;

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AdminApplicationDaoJdbcImpl(JdbcTemplate jdbcTemplate, AdminApplicationRowMapper rowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<AdminApplication> getAllApplications(){
        String query = "SELECT \n" +
                "    s.first_name, \n" +
                "    s.last_name, \n" +
                "    s.email, \n" +
                "    c.name AS course_name, \n" +
                "    app.class_id, \n" +
                "    sem.name AS semester_name, \n" +
                "    app.request, \n" +
                "    app.creation_time, \n" +
                "    app.status, \n" +
                "    s.id AS student_id, \n" +
                "    app.id AS application_id \n" +
                "FROM \n" +
                "    Application app \n" +
                "    JOIN Student s ON app.student_id = s.id \n" +
                "    JOIN WebRegClass cl ON app.class_id = cl.id \n" +
                "    JOIN Course c ON cl.course_id = c.id \n" +
                "    JOIN Semester sem ON cl.semester_id = sem.id \n" +
                "WHERE \n" +
                "    app.status = 'pending' \n" +
                "ORDER BY \n" +
                "    app.creation_time DESC";
        List<AdminApplication> adminApplications = jdbcTemplate.query(query, rowMapper);
        return adminApplications.size() == 0 ? null : adminApplications;
    }


    public void updateApplicationStatus(Integer applicationId, String status, String description) {
        // Update the status and description of the application with the given application_id in the Application table
        String updateStatusQuery = "UPDATE Application SET status = ?, feedback = ? WHERE id = ?";
        jdbcTemplate.update(updateStatusQuery, status, description, applicationId);
    }

    public String getRequestByApplicationId(Integer applicationId) {
        // Get the request of the application with the given application_id in the Application table
        String getRequestQuery = "SELECT request FROM Application WHERE id = ?";
        return jdbcTemplate.queryForObject(getRequestQuery, String.class, applicationId);
    }

    public void addStudentToClassByApplicationId(Integer applicationId) {
        // Retrieve the application details
        String queryGetAppDetails = "SELECT student_id, class_id FROM Application WHERE id = ?";
        Map<String, Object> applicationDetails = jdbcTemplate.queryForMap(queryGetAppDetails, applicationId);

        // Retrieve the student_id and class_id from the application details
        int studentId = (int) applicationDetails.get("student_id");
        int classId = (int) applicationDetails.get("class_id");

        // Add the student to the class with the status 'ongoing'
        String queryAddStudentToClass = "INSERT INTO StudentClass (student_id, class_id, status) VALUES (?, ?, 'ongoing')";
        jdbcTemplate.update(queryAddStudentToClass, studentId, classId);

        // Increment the enrollment number for the class
        String queryUpdateEnrollment = "UPDATE WebRegClass SET enrollment_num = enrollment_num + 1 WHERE id = ?";
        jdbcTemplate.update(queryUpdateEnrollment, classId);
    }

    public void withdrawStudentFromClassByApplicationId(Integer applicationId) {
        // Retrieve the application details
        String queryGetAppDetails = "SELECT student_id, class_id FROM Application WHERE id = ?";
        Map<String, Object> applicationDetails = jdbcTemplate.queryForMap(queryGetAppDetails, applicationId);

        // Retrieve the student_id and class_id from the application details
        int studentId = (int) applicationDetails.get("student_id");
        int classId = (int) applicationDetails.get("class_id");

        // Update the status of the student in the class to 'withdraw'
        String queryWithdrawStudentFromClass = "UPDATE StudentClass SET status = 'withdraw' WHERE student_id = ? AND class_id = ?";
        jdbcTemplate.update(queryWithdrawStudentFromClass, studentId, classId);

        // Decrement the enrollment number for the class
        String queryUpdateEnrollment = "UPDATE WebRegClass SET enrollment_num = enrollment_num - 1 WHERE id = ?";
        jdbcTemplate.update(queryUpdateEnrollment, classId);
    }
}
