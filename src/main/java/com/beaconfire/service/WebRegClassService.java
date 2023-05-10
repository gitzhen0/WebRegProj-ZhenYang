package com.beaconfire.service;

import com.beaconfire.dao.*;
import com.beaconfire.dao.hibernate.*;
import com.beaconfire.dao.jdbc.*;
import com.beaconfire.domain.jdbc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class WebRegClassService {


    private WebRegClassDisplayDao webRegClassDisplayDao;


    private ClassToSemesterDisplayDao classToSemesterDisplayDao;

    private ClassToClassroomDisplayDao classToClassroomDisplayDao;


    private ClassToPrerequisiteDisplayDao classToPrerequisiteDisplayDao;


    private ClassToProfessorDisplayDao classToProfessorDisplayDao;


    private ClassToLectureDisplayDao classToLectureDisplayDao;



    @Autowired
    public void setDao(@Qualifier("webRegClassDisplayDaoJdbcImpl") WebRegClassDisplayDaoJdbcImpl webRegClassDisplayDaoJdbcImpl,
                       @Qualifier("webRegClassDisplayDaoHibernateImpl") WebRegClassDisplayDaoHibernateImpl webRegClassDisplayDaoHibernateImpl,
                       @Qualifier("classToSemesterDisplayDaoJdbcImpl") ClassToSemesterDisplayDao classToSemesterDisplayDao,
                       @Qualifier("classToSemesterDisplayDaoHibernateImpl") ClassToSemesterDisplayDaoHibernateImpl classToSemesterDisplayDaoHibernateImpl,
                       @Qualifier("classToClassroomDisplayDaoJdbcImpl") ClassToClassroomDisplayDao classToClassroomDisplayDao,
                       @Qualifier("classToClassroomDisplayDaoHibernateImpl") ClassToClassroomDisplayDaoHibernateImpl classToClassroomDisplayDaoHibernateImpl,
                       @Qualifier("classToPrerequisiteDisplayDaoJdbcImpl") ClassToPrerequisiteDisplayDao classToPrerequisiteDisplayDao,
                       @Qualifier("classToPrerequisiteDisplayDaoHibernateImpl") ClassToPrerequisiteDisplayDaoHibernateImpl classToPrerequisiteDisplayDaoHibernateImpl,
                       @Qualifier("classToProfessorDisplayDaoJdbcImpl") ClassToProfessorDisplayDao classToProfessorDisplayDao,
                       @Qualifier("classToProfessorDisplayDaoHibernateImpl") ClassToProfessorDisplayDaoHibernateImpl classToProfessorDisplayDaoHibernateImpl,
                       @Qualifier("classToLectureDisplayDaoJdbcImpl") ClassToLectureDisplayDao classToLectureDisplayDao,
                       @Qualifier("classToLectureDisplayDaoHibernateImpl") ClassToLectureDisplayDaoHibernateImpl classToLectureDisplayDaoHibernateImpl,
                       @Qualifier("ormSwitch") boolean ormSwitch) {

        this.webRegClassDisplayDao = ormSwitch ? webRegClassDisplayDaoHibernateImpl : webRegClassDisplayDaoJdbcImpl;
        this.classToSemesterDisplayDao = ormSwitch ? classToSemesterDisplayDaoHibernateImpl : classToSemesterDisplayDao;
        this.classToClassroomDisplayDao = ormSwitch ? classToClassroomDisplayDaoHibernateImpl : classToClassroomDisplayDao;
        this.classToPrerequisiteDisplayDao = ormSwitch ? classToPrerequisiteDisplayDaoHibernateImpl : classToPrerequisiteDisplayDao;
        this.classToProfessorDisplayDao = ormSwitch ? classToProfessorDisplayDaoHibernateImpl : classToProfessorDisplayDao;
        this.classToLectureDisplayDao = ormSwitch ? classToLectureDisplayDaoHibernateImpl : classToLectureDisplayDao;

    }






    public WebRegClassDisplay getWebRegClassDisplayByClassId(int class_id) {
        return webRegClassDisplayDao.getWebRegClassDisplayByClassId(class_id);
    }

    public ClassToSemesterDisplay getClassToSemesterDisplayByClassId(int class_id) {
        return classToSemesterDisplayDao.getSemesterbyClassId(class_id);
    }

    public ClassToClassroomDisplay getClassToClassroomDisplayByClassId(int class_id) {
        return classToClassroomDisplayDao.getClassroomByClassId(class_id);
    }

    public ClassToPrerequisiteDisplay getClassToPrerequisiteDisplayByClassId(int class_id) {
        return classToPrerequisiteDisplayDao.getPrerequisiteByClassId(class_id);
    }

    public ClassToProfessorDisplay getClassToProfessorDisplayByClassId(int class_id) {
        return classToProfessorDisplayDao.getProfessorByClassId(class_id);
    }

    public ClassToLectureDisplay getClassToLectureDisplayByClassId(int class_id) {
        return classToLectureDisplayDao.getLectureByClassId(class_id);
    }


}
