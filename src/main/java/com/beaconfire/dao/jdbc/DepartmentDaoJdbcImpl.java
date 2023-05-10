package com.beaconfire.dao.jdbc;

import com.beaconfire.domain.jdbc.Department;
import com.beaconfire.mapper.DepartmentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("departmentDaoJdbcImpl")
public class DepartmentDaoJdbcImpl implements com.beaconfire.dao.DepartmentDao{

    JdbcTemplate jdbcTemplate;

    @Autowired
    DepartmentRowMapper rowMapper;

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DepartmentDaoJdbcImpl(JdbcTemplate jdbcTemplate, DepartmentRowMapper rowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Department> getAllDepartments(){
        String query = "SELECT * FROM Department";
        List<Department> departments = jdbcTemplate.query(query, rowMapper);
        return departments;
    }
}
