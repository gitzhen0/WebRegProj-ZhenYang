package com.beaconfire.service;

import com.beaconfire.dao.ClassManagementDisplayDao;
import com.beaconfire.dao.hibernate.ClassManagementDisplayDaoHibernateImpl;
import com.beaconfire.dao.jdbc.ClassManagementDisplayDaoJdbcImpl;
import com.beaconfire.domain.jdbc.ClassManagementDisplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassManagementService {


    ClassManagementDisplayDao classManagementDisplayDao;

    @Autowired
    public void setDao(@Qualifier("classManagementDisplayDaoHibernateImpl") ClassManagementDisplayDaoHibernateImpl classManagementDisplayDaoHibernateImpl, @Qualifier("classManagementDisplayDaoJdbcImpl") ClassManagementDisplayDaoJdbcImpl classManagementDisplayDaoJdbcImpl, @Qualifier("ormSwitch") boolean ormSwitch) {
        this.classManagementDisplayDao = ormSwitch ? classManagementDisplayDaoHibernateImpl : classManagementDisplayDaoJdbcImpl;
    }

    public List<ClassManagementDisplay> getClassManagementDisplay(int classId){
        return classManagementDisplayDao.getClassManagementDisplayByStudentId(classId);
    }
}
