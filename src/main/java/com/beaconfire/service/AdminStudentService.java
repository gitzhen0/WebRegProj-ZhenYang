package com.beaconfire.service;

import com.beaconfire.dao.AdminStudentDisplayDao;
import com.beaconfire.dao.hibernate.AdminStudentDisplayDaoHibernateImpl;
import com.beaconfire.dao.jdbc.AdminStudentDisplayDaoJdbcImpl;
import com.beaconfire.domain.jdbc.AdminHomeDisplay;
import com.beaconfire.domain.jdbc.StudentClassDisplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminStudentService {


    private AdminStudentDisplayDao adminStudentDisplayDao;

    @Autowired
    public void setDao(@Qualifier("adminStudentDisplayDaoJdbcImpl") AdminStudentDisplayDaoJdbcImpl adminStudentDisplayDaoJdbcImpl, @Qualifier("adminStudentDisplayDaoHibernateImpl") AdminStudentDisplayDaoHibernateImpl adminStudentDisplayDaoHibernateImpl, @Qualifier("ormSwitch") boolean ormSwitch) {
        this.adminStudentDisplayDao = ormSwitch ? adminStudentDisplayDaoHibernateImpl : adminStudentDisplayDaoJdbcImpl;
    }

    public void UpdateStudentStatus(int studentId, int status) {
        adminStudentDisplayDao.UpdateStudentStatus(studentId, status);

    }

    public List<StudentClassDisplay> getStudentClassByStudentId(int studentId){
        return adminStudentDisplayDao.getStudentClassesByStudentId(studentId);
    }

    public AdminHomeDisplay getAdminHomeDisplay(int userId) {

        AdminHomeDisplay adminHomeDisplay = adminStudentDisplayDao.getStudent(userId);


            String ac = adminHomeDisplay.getIs_active();
            if(ac.equals("1")){
                adminHomeDisplay.setIs_active("Active");
            }else{
                adminHomeDisplay.setIs_active("Inactive");
            }

       return adminHomeDisplay;
    }

    public void flipStudentStatus(int studentId){
        adminStudentDisplayDao.flipStudentStatus(studentId);
    }

    public void changeStudentClassStatus(int studentId, int classId, String status) {
        adminStudentDisplayDao.changeStudentClassStatus(studentId, classId, status);
    }
}
