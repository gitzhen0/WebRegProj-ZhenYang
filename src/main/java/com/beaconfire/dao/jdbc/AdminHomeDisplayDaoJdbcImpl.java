package com.beaconfire.dao.jdbc;

import com.beaconfire.dao.AdminHomeDisplayDao;
import com.beaconfire.domain.jdbc.AdminHomeDisplay;
import com.beaconfire.mapper.AdminHomeDisplayRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("adminHomeDisplayDaoJdbcImpl")
public class AdminHomeDisplayDaoJdbcImpl implements AdminHomeDisplayDao {

    JdbcTemplate jdbcTemplate;

    AdminHomeDisplayRowMapper rowMapper;

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AdminHomeDisplayDaoJdbcImpl(JdbcTemplate jdbcTemplate, AdminHomeDisplayRowMapper rowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }



    public List<AdminHomeDisplay> findPaginated(int page, int limit) {
        int offset = (page - 1) * limit;
        String sql = "SELECT st.id AS student_id, st.first_name, st.last_name, st.email, d.name AS department_name, d.school AS school_name, st.is_active " +
                "FROM Student st " +
                "JOIN Department d ON st.department_id = d.id " +
                "WHERE st.is_admin = 0 " +
                "LIMIT :offset, :limit";
        Map<String, Object> params = new HashMap<>();
        params.put("offset", offset);
        params.put("limit", limit);
        return namedParameterJdbcTemplate.query(sql, params, rowMapper);
    }

    public int getTotalPages(int limit) {
        String sql = "SELECT COUNT(*) FROM Student WHERE is_admin=0";
        int totalRecords = jdbcTemplate.queryForObject(sql, Integer.class);
        return (int) Math.ceil((double) totalRecords / limit);
    }


}
