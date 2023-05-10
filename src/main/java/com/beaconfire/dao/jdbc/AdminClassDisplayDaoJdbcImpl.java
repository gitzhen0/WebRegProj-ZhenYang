package com.beaconfire.dao.jdbc;


import com.beaconfire.dao.AdminClassDisplayDao;
import com.beaconfire.domain.jdbc.AdminClassDisplay;
import com.beaconfire.mapper.AdminClassDisplayRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.List;

@Repository("adminClassDisplayDaoJdbcImpl")
public class AdminClassDisplayDaoJdbcImpl implements AdminClassDisplayDao {

    JdbcTemplate jdbcTemplate;

    AdminClassDisplayRowMapper rowMapper;

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AdminClassDisplayDaoJdbcImpl(JdbcTemplate jdbcTemplate, AdminClassDisplayRowMapper rowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<AdminClassDisplay> getAllClasses() {
        String query = "SELECT\n" +
                "    c.id AS class_id,\n" +
                "    co.id AS course_id,\n" +
                "    co.name AS course_name,\n" +
                "    co.code AS course_code,\n" +
                "    d.name AS department_name,\n" +
                "    d.school AS school_name,\n" +
                "    c.capacity AS capacity,\n" +
                "    c.enrollment_num AS enrollment_num,\n" +
                "    CASE\n" +
                "        WHEN c.is_active = 1 THEN 'true'\n" +
                "        ELSE 'false'\n" +
                "    END AS is_active,\n" +
                "    sem.name AS semester_name\n" +
                "FROM\n" +
                "    WebRegClass c\n" +
                "JOIN\n" +
                "    Course co ON c.course_id = co.id\n" +
                "JOIN\n" +
                "    Department d ON co.department_id = d.id\n" +
                "JOIN\n" +
                "    Semester sem ON c.semester_id = sem.id\n" +
                "ORDER BY\n" +
                "    sem.start_date DESC,\n" +
                "    CASE\n" +
                "        WHEN c.is_active = 1 THEN 1\n" +
                "        ELSE 0\n" +
                "    END DESC";
        List<AdminClassDisplay> adminClassDisplays = jdbcTemplate.query(query, rowMapper);
        return adminClassDisplays.size() == 0 ? null : adminClassDisplays;

    }

    public int addNewClass(int course_id, int professor_id, int semester_id, int classroom_id, int capacity, String dayOfWeek, LocalTime startTime, LocalTime endTime) {
        String query1 = "INSERT INTO WebRegClass (course_id, professor_id, semester_id, classroom_id, capacity) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, course_id);
            preparedStatement.setInt(2, professor_id);
            preparedStatement.setInt(3, semester_id);
            preparedStatement.setInt(4, classroom_id);
            preparedStatement.setInt(5, capacity);
            return preparedStatement;
        }, keyHolder);

        int class_id = keyHolder.getKey().intValue();

        String query2 = "INSERT INTO Lecture (class_id, day_of_the_week, start_time, end_time) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(query2, preparedStatement -> {
            preparedStatement.setInt(1, class_id);
            preparedStatement.setString(2, dayOfWeek);
            preparedStatement.setTime(3, java.sql.Time.valueOf(startTime));
            preparedStatement.setTime(4, java.sql.Time.valueOf(endTime));
        });

        return class_id;
    }


    public void flipClassStatus(int classId) {
        // Get the current status of the class
        String getStatusQuery = "SELECT is_active FROM WebRegClass WHERE id = ?";
        boolean currentStatus = jdbcTemplate.queryForObject(getStatusQuery, Boolean.class, classId);

        // Flip the status
        boolean newStatus = !currentStatus;

        // Update the class's status in the database
        String updateStatusQuery = "UPDATE WebRegClass SET is_active = ? WHERE id = ?";
        jdbcTemplate.update(updateStatusQuery, newStatus, classId);
    }
}
