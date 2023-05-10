package com.beaconfire.dao.jdbc;

import com.beaconfire.dao.StudentClassDisplayDao;
import com.beaconfire.domain.jdbc.AdminClassToStudentDisplay;
import com.beaconfire.domain.jdbc.StudentClassDisplay;
import com.beaconfire.mapper.AdminClassToStudentDisplayRowMapper;
import com.beaconfire.mapper.StudentClassDisplayRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("studentClassDisplayDaoJdbcImpl")
public class StudentClassDisplayDaoJdbcImpl implements StudentClassDisplayDao {
    JdbcTemplate jdbcTemplate;

    StudentClassDisplayRowMapper SCDrowMapper;


    AdminClassToStudentDisplayRowMapper ACSDrowMapper;

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public StudentClassDisplayDaoJdbcImpl(JdbcTemplate jdbcTemplate, StudentClassDisplayRowMapper SCDrowMapper, AdminClassToStudentDisplayRowMapper ACSDrowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.SCDrowMapper = SCDrowMapper;
        this.ACSDrowMapper = ACSDrowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    // trial below
    public List<StudentClassDisplay> findPaginated(int page, int limit, HttpSession session) {
        int offset = (page - 1) * limit;
        int userId = (int) session.getAttribute("userId");

        String sql = "SELECT * FROM studentclassview Where student_id = :userId LIMIT :offset, :limit";

        Map<String, Object> params = new HashMap<>();
        params.put("offset", offset);
        params.put("limit", limit);
        params.put("userId", userId);


        return namedParameterJdbcTemplate.query(sql, params, SCDrowMapper);
    }

    public int getTotalPages(int limit, HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        String sql = "SELECT COUNT(*) FROM studentclassview Where student_id = ?";
        int totalRecords = jdbcTemplate.queryForObject(sql, Integer.class, userId);

        return (int) Math.ceil((double) totalRecords / limit);
    }



    public List<AdminClassToStudentDisplay> findStudentsByClassId(int classId) {
        String sql = "SELECT\n" +
                "    sc.class_id AS class_id,\n" +
                "    s.id AS student_id,\n" +
                "    s.first_name AS first_name,\n" +
                "    s.last_name AS last_name,\n" +
                "    s.email AS email,\n" +
                "    d.name AS department_name,\n" +
                "    d.school AS school_name\n" +
                "FROM\n" +
                "    Student s\n" +
                "JOIN\n" +
                "    StudentClass sc ON s.id = sc.student_id\n" +
                "JOIN\n" +
                "    Department d ON s.department_id = d.id\n" +
                "WHERE\n" +
                "    sc.class_id = ? \n" +
                "    AND sc.status = 'ongoing'";

        System.out.println("findStudentsByClassId: is called");
        List<AdminClassToStudentDisplay> adminClassToStudentDisplays = jdbcTemplate.query(sql, ACSDrowMapper, classId);
        System.out.println("findStudentsByClassId: before return");
        return adminClassToStudentDisplays.size() == 0 ? null : adminClassToStudentDisplays;
    }



}
