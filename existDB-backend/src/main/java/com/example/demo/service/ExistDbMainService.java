package com.example.demo.service;

import com.example.demo.domain.ExistDetails;
import com.example.demo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ExistDbMainService {

    private Util util;
    private ExistDbUserManagerServices existDbUserManagerServices;

    @Autowired
    public void setUtil(Util util){
        this.util = util;
    }

    @Autowired
    public void setExistDbUserManagerServices(ExistDbUserManagerServices existDbUserManagerServices){
        this.existDbUserManagerServices = existDbUserManagerServices;
    }

//    public boolean InitializeDbDriver(ExistDbConnectionForm existDbConnectionForm){
//        try{
//            this.details = new ExistDetails();
//            util.initDatabaseDriver(this.details.getDriver());
//        }catch (ClassNotFoundException e){
//            System.out.println("DriverClassNotFoundException: " + e.getMessage());
//        }catch (InstantiationException e){
//            System.out.println("InstantiationException: " + e.getMessage());
//        }catch (IllegalAccessException e){
//            System.out.println("IllegalAccessException: " + e.getMessage());
//        }catch (XMLDBException e){
//            System.out.println("XMLDBException: " + e.getMessage());
//        }
//
//        details.setUrl(existDbConnectionForm.getUrl());
//        details.setUsername(existDbConnectionForm.getUsername());
//        details.setPassword(existDbConnectionForm.getPassword());
//        System.out.println("Driver initialized");
//        return true;
//    }

    public void listUsers() {
        ExistDetails details = new ExistDetails();
        try{
            System.out.println(existDbUserManagerServices.listUsers(details));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isAdmin (String username, String password){
        ExistDetails details = new ExistDetails();
        util.initDatabaseDriver(details.getDriver());
        details.setUsername(username);
        details.setPassword(password);
        return existDbUserManagerServices.isAndminAccess(details);
    }

}
