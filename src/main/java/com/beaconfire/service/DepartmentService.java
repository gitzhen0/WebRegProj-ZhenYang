package com.beaconfire.service;

import com.beaconfire.dao.DepartmentDao;
import com.beaconfire.dao.hibernate.DepartmentDaoHibernateImpl;
import com.beaconfire.dao.jdbc.DepartmentDaoJdbcImpl;
import com.beaconfire.domain.jdbc.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private DepartmentDao departmentDao;

    @Autowired
    public void setDao(@Qualifier("departmentDaoJdbcImpl") DepartmentDaoJdbcImpl departmentDaoJdbcImpl,
                       @Qualifier("departmentDaoHibernateImpl") DepartmentDaoHibernateImpl departmentDaoHibernateImpl,
                       @Qualifier("ormSwitch") boolean ormSwitch) {
        this.departmentDao = ormSwitch ? departmentDaoHibernateImpl : departmentDaoJdbcImpl;
    }
    public List<Department> getAllDepartments(){
        return departmentDao.getAllDepartments();
    }
}
