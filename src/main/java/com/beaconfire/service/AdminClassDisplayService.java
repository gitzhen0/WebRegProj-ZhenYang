package com.beaconfire.service;

import com.beaconfire.dao.AdminClassDisplayDao;
import com.beaconfire.dao.hibernate.AdminClassDisplayDaoHibernateImpl;
import com.beaconfire.dao.jdbc.AdminClassDisplayDaoJdbcImpl;
import com.beaconfire.domain.jdbc.AdminClassDisplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class AdminClassDisplayService {


    private AdminClassDisplayDao adminClassDisplayDao;

    @Autowired
    public void setDao(@Qualifier("adminClassDisplayDaoJdbcImpl") AdminClassDisplayDaoJdbcImpl adminClassDisplayDaoJdbcImpl, @Qualifier("adminClassDisplayDaoHibernateImpl") AdminClassDisplayDaoHibernateImpl adminClassDisplayDaoHibernateImpl, @Qualifier("ormSwitch") boolean ormSwitch) {
        this.adminClassDisplayDao = ormSwitch ? adminClassDisplayDaoHibernateImpl : adminClassDisplayDaoJdbcImpl;
    }

    public List<AdminClassDisplay> getAdminClassDisplays() {
        return adminClassDisplayDao.getAllClasses();
    }

    public int addNewClass(int course_id, int professor_id, int semester_id, int classroom_id, int capacity, String dayOfWeek, LocalTime startTime, LocalTime endTime){
        return adminClassDisplayDao.addNewClass(course_id, professor_id, semester_id, classroom_id, capacity, dayOfWeek, startTime, endTime);
    }

    public void flipClassStatus(int classId) {
        adminClassDisplayDao.flipClassStatus(classId);
    }
}
