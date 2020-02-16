package com.example.demo.service;

import com.example.demo.domain.ExistDBUsers;
import com.example.demo.domain.ExistDetails;
import com.example.demo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class ExistDbMainService {

    @Autowired
    private Util util;

    @Autowired
    private ExistDbUserManagerServices existDbUserManagerServices;

    @Autowired
    private ExistDbCollectionManagerService existDbCollectionManagerService;

    public ArrayList<ExistDBUsers> listUsers() {
        ExistDetails details = new ExistDetails();
        List<String> users = new ArrayList<>();
        ArrayList<ExistDBUsers> existDBUsers = new ArrayList<>();

        //csak teszt miatt kell-------------------------------//
        util.initDatabaseDriver(details.getDriver());
        details.setUsername("admin");
        details.setPassword("admin1234");
        //----------------------------------------------------//

        try{
            users = Arrays.asList(existDbUserManagerServices.listUsers(details).split("\n"));
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
        return existDBUsers;
    }

    public boolean deleteUser(String username){
        ExistDetails details = new ExistDetails();

        //csak teszt miatt kell-------------------------------//
        util.initDatabaseDriver(details.getDriver());
        details.setUsername("admin");
        details.setPassword("admin1234");
        //----------------------------------------------------//

        return existDbUserManagerServices.deleteUser(details, username);
    }

    public boolean isAdmin (String username, String password, String url){
        ExistDetails details = new ExistDetails();
        util.initDatabaseDriver(details.getDriver());
        details.setUrl(url);
        details.setUsername(username);
        details.setPassword(password);
        return existDbUserManagerServices.isAndminAccess(details);
    }

    public List<String> getCollection() {
        ExistDetails details = new ExistDetails();

        //csak teszt miatt kell-------------------------------//
        util.initDatabaseDriver(details.getDriver());
        details.setUsername("admin");
        details.setPassword("admin1234");
        //----------------------------------------------------//

        return Arrays.asList(existDbCollectionManagerService.getCollectionContent(details, "").split("\n"));
    }
}
