package com.beaconfire.dao.jdbc;

import com.beaconfire.dao.WebRegClassDisplayDao;
import com.beaconfire.domain.jdbc.WebRegClassDisplay;
import com.beaconfire.mapper.WebRegClassDisplayRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("webRegClassDisplayDaoJdbcImpl")
public class WebRegClassDisplayDaoJdbcImpl implements WebRegClassDisplayDao {
    JdbcTemplate jdbcTemplate;

    WebRegClassDisplayRowMapper rowMapper;

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public WebRegClassDisplayDaoJdbcImpl(JdbcTemplate jdbcTemplate, WebRegClassDisplayRowMapper rowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public WebRegClassDisplay getWebRegClassDisplayById(Integer id){
        String query = "SELECT * FROM WebRegClassDisplay WHERE id = ?";
        List<WebRegClassDisplay> webRegClassDisplays = jdbcTemplate.query(query, rowMapper, id);
        return webRegClassDisplays.size() == 0 ? null : webRegClassDisplays.get(0);
    }

    public WebRegClassDisplay getWebRegClassDisplayByClassId(Integer id) {
        String query = "SELECT cl.id AS class_id,\n" +
                "       c.name AS course_name,\n" +
                "       c.code AS course_code,\n" +
                "       d.name AS department_name,\n" +
                "       d.school AS school_name,\n" +
                "       c.description AS course_description,\n" +
                "       cl.capacity AS capacity,\n" +
                "       cl.enrollment_num AS enrollment_num,\n" +
                "       cl.is_active AS is_active\n" +
                "FROM WebRegClass cl\n" +
                "JOIN Course c ON cl.course_id = c.id\n" +
                "JOIN Department d ON c.department_id = d.id\n" +
                "WHERE cl.id = ?";
        List<WebRegClassDisplay> webRegClassDisplays = jdbcTemplate.query(query, rowMapper, id);
        return webRegClassDisplays.size() == 0 ? null : webRegClassDisplays.get(0);
    }


    //use jdbc template write a method with a query that can select course name, course code, department name, school name, course description, course capacity, current enrollment number, isActive from my database



}
