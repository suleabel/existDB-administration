package com.example.demo.service;

import com.example.demo.domain.ExistDBUsers;
import com.example.demo.domain.ExistDetails;
import com.example.demo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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

    public ArrayList<ExistDBUsers> listUsers() {
        ExistDetails details = new ExistDetails();
        List<String> users = new ArrayList<>();
        ArrayList<ExistDBUsers> existDBUsers = new ArrayList<>();

        String result = null;

        //csak teszt miatt kell-------------------------------//
        util.initDatabaseDriver(details.getDriver());
        details.setUsername("admin");
        details.setPassword("admin1234");
        //----------------------------------------------------//

        System.out.println(details.toString());
        try{
            result = existDbUserManagerServices.listUsers(details);
            users = Arrays.asList(result.split("\n"));
        }catch (Exception e){
            e.printStackTrace();
        }

        for (String user: users) {
            System.out.println(user);
            ExistDBUsers existDBUser = new ExistDBUsers();
            existDBUser.setUsername(user);
            existDBUser.setGroups(Arrays.asList(existDbUserManagerServices.getUserGroups(details, user).split("\n")));
            existDBUser.setUmask(existDbUserManagerServices.getUserUmask(details, user));
            existDBUser.setPrimaryGroup(existDbUserManagerServices.getUserPrimaryGroup(details, user));
            existDBUsers.add(existDBUser);
        }

        System.out.println(existDBUsers.toString());

        return existDBUsers;
    }

    public boolean isAdmin (String username, String password, String url){
        ExistDetails details = new ExistDetails();
        util.initDatabaseDriver(details.getDriver());
        details.setUrl(url);
        details.setUsername(username);
        details.setPassword(password);
        return existDbUserManagerServices.isAndminAccess(details);
    }

}
