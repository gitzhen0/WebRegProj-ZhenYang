package com.beaconfire.service;

import com.beaconfire.dao.AdminHomeDisplayDao;
import com.beaconfire.dao.hibernate.AdminHomeDisplayDaoHibernateImpl;
import com.beaconfire.dao.jdbc.AdminHomeDisplayDaoJdbcImpl;
import com.beaconfire.domain.jdbc.AdminHomeDisplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;


@Service
public class AdminHomeService {


    private AdminHomeDisplayDao adminHomeDisplayDao;

    @Autowired
    public void setDao(@Qualifier("adminHomeDisplayDaoJdbcImpl") AdminHomeDisplayDaoJdbcImpl adminHomeDisplayDaoJdbcImpl, @Qualifier("adminHomeDisplayDaoHibernateImpl") AdminHomeDisplayDaoHibernateImpl adminHomeDisplayDaoHibernateImpl, @Qualifier("ormSwitch") boolean ormSwitch) {
        this.adminHomeDisplayDao = ormSwitch ? adminHomeDisplayDaoHibernateImpl : adminHomeDisplayDaoJdbcImpl;
    }

    public String displayAdminHomeStudents(int page, int limit, Model model){
        List<AdminHomeDisplay> adminHomeStudents = adminHomeDisplayDao.findPaginated(page, limit);


        for(AdminHomeDisplay tmp : adminHomeStudents){
            String ac = tmp.getIs_active();
            if(ac.equals("1")){
                tmp.setIs_active("Active");
            }else{
                tmp.setIs_active("Inactive");
            }
        }


        int totalPages = adminHomeDisplayDao.getTotalPages(limit);
        model.addAttribute("adminHomeDisplays", adminHomeStudents);
        model.addAttribute("page", page);
        model.addAttribute("limit", limit);
        model.addAttribute("totalPages", totalPages);
        System.out.println("admin home service redirecting to admin home");
        return "adminHome";
    }


}
