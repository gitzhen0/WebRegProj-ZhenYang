package com.beaconfire.dao.jdbc;

import com.beaconfire.dao.StudentClassDao;
import com.beaconfire.domain.jdbc.StudentClass;
import com.beaconfire.mapper.StudentClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.sql.Date;

@Repository("studentClassDaoJdbcImpl")
public class StudentClassDaoJdbcImpl implements StudentClassDao {

    JdbcTemplate jdbcTemplate;

    StudentClassRowMapper rowMapper;

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public StudentClassDaoJdbcImpl(JdbcTemplate jdbcTemplate, StudentClassRowMapper rowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }



    public List<StudentClass> getStudentClassByStudentId(int studentId){
        String query = "SELECT * FROM StudentClass WHERE student_id = ?";
        List<StudentClass> studentClasses = jdbcTemplate.query(query, rowMapper, studentId);
        return studentClasses;
    }

    public int check0IsStudentEnrolledInClass(int studentId, int classId) {
        String sql = "SELECT COUNT(*) FROM StudentClass sc WHERE sc.student_id = ? AND sc.class_id = ? AND sc.status IN ('ongoing')";
//        String sql = "SELECT COUNT(*) FROM StudentClass sc WHERE sc.student_id = ? AND sc.class_id = ? AND sc.status IN ('ongoing', 'pass', 'fail', 'withdraw')";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, studentId, classId);
        return count > 0 ? 1 : 0;
    }



    public int checkAStudentIsActive(int studentId){
        String query = "SELECT \n" +
                "    s.is_active\n" +
                "FROM\n" +
                "    Student s\n" +
                "WHERE\n" +
                "    s.id = ?";
        int isActive = jdbcTemplate.queryForObject(query, Integer.class, studentId);
        return isActive;
    }

    public int checkBClassIsActive(int classId){
        String sql = "SELECT cl.is_active FROM WebRegClass cl WHERE cl.id = ?";
        int isActive = jdbcTemplate.queryForObject(sql, Integer.class, classId);
        return isActive;
    }

    public int checkCStudentPassPrerequisite(int studentId, int classId){
        String sql = "SELECT \n" +
                "    CASE \n" +
                "        WHEN COUNT(pr.pre_course_id) = COUNT(DISTINCT sc.id) THEN 1\n" +
                "        ELSE 0\n" +
                "    END AS prerequisites_passed\n" +
                "FROM\n" +
                "    Prerequisite pr\n" +
                "LEFT JOIN\n" +
                "    StudentClass sc ON pr.pre_course_id = sc.class_id\n" +
                "    AND sc.student_id = ? AND sc.status = 'pass'\n" +
                "WHERE\n" +
                "    pr.course_id = (\n" +
                "        SELECT course_id FROM WebRegClass WHERE id = ?\n" +
                "    )\n" +
                ";\n";
        int result = jdbcTemplate.queryForObject(sql, Integer.class, studentId, classId);
        return result;
    }

    public int checkDNoLectureConflict(int studentId, int classId){
        String sql = "SELECT " +
                "    CASE " +
                "        WHEN COUNT(*) > 0 THEN 0" +
                "        ELSE 1" +
                "    END AS no_time_conflict " +
                "FROM " +
                "    Lecture l1 " +
                "JOIN " +
                "    Lecture l2 ON l1.day_of_the_week = l2.day_of_the_week " +
                "    AND ( " +
                "        (l1.start_time >= l2.start_time AND l1.start_time < l2.end_time) " +
                "        OR (l1.end_time > l2.start_time AND l1.end_time <= l2.end_time) " +
                "        OR (l1.start_time <= l2.start_time AND l1.end_time >= l2.end_time) " +
                "    ) " +
                "JOIN " +
                "    StudentClass sc ON sc.class_id = l1.class_id " +
                "    AND sc.student_id = ? AND sc.status = 'ongoing' " +
                "WHERE " +
                "    l2.class_id = ?;";
        return jdbcTemplate.queryForObject(sql, Integer.class, studentId, classId);
    }

    public int checkEClassIsFull(int classId){
        String sql = "SELECT " +
                "    CASE " +
                "        WHEN enrollment_num >= capacity THEN 0" +
                "        ELSE 1" +
                "    END AS is_not_full " +
                "FROM " +
                "    WebRegClass " +
                "WHERE " +
                "    id = ?;";

        return jdbcTemplate.queryForObject(sql, Integer.class, classId);
    }

    public int checkFIfStudentNeverPassed(int studentId, int courseId) {
        String sql = "SELECT " +
                "    CASE " +
                "        WHEN COUNT(*) > 0 THEN 0" +
                "        ELSE 1" +
                "    END AS never_passed " +
                "FROM " +
                "    StudentClass sc " +
                "JOIN " +
                "    WebRegClass cl ON sc.class_id = cl.id " +
                "WHERE " +
                "    sc.student_id = ? " +
                "    AND cl.course_id = ? " +
                "    AND sc.status = 'pass';";

        return jdbcTemplate.queryForObject(sql, Integer.class, studentId, courseId);
    }

    public int checkXisStudentEnrolledInClassWithinTwoWeeks(int studentId, int classId) {
        String sql = "SELECT sc.status, sem.start_date " +
                "FROM StudentClass sc " +
                "JOIN WebRegClass cl ON sc.class_id = cl.id " +
                "JOIN Semester sem ON cl.semester_id = sem.id " +
                "WHERE sc.student_id = ? AND cl.id = ?";

        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, studentId, classId);

        for (Map<String, Object> result : results) {
            String status = (String) result.get("status");
            LocalDate startDate = ((Date) result.get("start_date")).toLocalDate();
            LocalDate currentDate = LocalDate.now();
            long daysBetween = ChronoUnit.DAYS.between(startDate, currentDate);

            if (status.equals("ongoing") && daysBetween <= 14) {
                return 1;
            }
        }

        return 0;
    }

    public void addStudentToClass(int studentId, int classId) {
        String query = "INSERT INTO StudentClass (student_id, class_id, status) VALUES (?, ?, ?)";
        jdbcTemplate.update(query, preparedStatement -> {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, classId);
            preparedStatement.setString(3, "ongoing");
        });

        // Update the enrollment number for the class
        String updateQuery = "UPDATE WebRegClass SET enrollment_num = enrollment_num + 1 WHERE id = ?";
        jdbcTemplate.update(updateQuery, classId);
    }


}
