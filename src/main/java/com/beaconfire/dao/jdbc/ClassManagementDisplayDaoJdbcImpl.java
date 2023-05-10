package com.beaconfire.dao.jdbc;

import com.beaconfire.dao.ClassManagementDisplayDao;
import com.beaconfire.domain.jdbc.ClassManagementDisplay;
import com.beaconfire.mapper.ClassManagementDisplayRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("classManagementDisplayDaoJdbcImpl")
public class ClassManagementDisplayDaoJdbcImpl implements ClassManagementDisplayDao {
    JdbcTemplate jdbcTemplate;

    ClassManagementDisplayRowMapper rowMapper;

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ClassManagementDisplayDaoJdbcImpl(JdbcTemplate jdbcTemplate, ClassManagementDisplayRowMapper rowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<ClassManagementDisplay> getClassManagementDisplayByStudentId(int userId){
        String query = "SELECT cl.id AS class_id,\n" +
                "       c.name AS course_name,\n" +
                "       c.code AS course_code,\n" +
                "       d.name AS department_name,\n" +
                "       d.school AS school_name,\n" +
                "       cl.enrollment_num AS enrollment_num,\n" +
                "       cl.capacity AS capacity,\n" +
                "       s.name AS semester_name,\n" +
                "       CASE\n" +
                "           WHEN EXISTS (\n" +
                "               SELECT 1\n" +
                "               FROM StudentClass sc\n" +
                "               WHERE sc.student_id = ? AND sc.class_id = cl.id AND sc.status = 'ongoing'\n" +
                "           ) THEN 'Enrolled'\n" +
                "           ELSE 'Not Enrolled'\n" +
                "       END AS isEnrolled\n" +
                "FROM WebRegClass cl\n" +
                "JOIN Course c ON cl.course_id = c.id\n" +
                "JOIN Department d ON c.department_id = d.id\n" +
                "JOIN Semester s ON cl.semester_id = s.id\n" +
                "WHERE cl.is_active = 1\n" +
                "  AND s.start_date <= CURRENT_DATE\n" +
                "  AND s.end_date >= CURRENT_DATE\n" +
                "ORDER BY s.start_date";

        List<ClassManagementDisplay> classManagementDisplays = jdbcTemplate.query(query, rowMapper, userId);
        return classManagementDisplays.size() == 0 ? null : classManagementDisplays;
    }

}
