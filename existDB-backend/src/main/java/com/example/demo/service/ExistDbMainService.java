package com.example.demo.service;

import com.example.demo.model.ExistDBGroup;
import com.example.demo.model.ExistDBUsers;
import com.example.demo.model.ExistDetails;
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

    @Autowired
    private ExistDbGroupManagerServices existDbGroupManagerServices;

    public void initDatabaseDriver() {
        ExistDetails details = new ExistDetails();
        //csak teszt miatt kell-------------------------------//
        util.initDatabaseDriver(details.getDriver());
        details.setUsername("admin");
        details.setPassword("admin1234");
        //----------------------------------------------------//
    }

    public ArrayList<ExistDBUsers> listUsers() {
        ExistDetails details = new ExistDetails();
        List<String> users;
        ArrayList<ExistDBUsers> existDBUsers = new ArrayList<>();

        //csak teszt miatt kell-------------------------------//
        util.initDatabaseDriver(details.getDriver());
        details.setUsername("admin");
        details.setPassword("admin1234");
        //----------------------------------------------------//

        users = Arrays.asList(existDbUserManagerServices.listUsers(details).split("\n"));

        for (String user: users) {
            System.out.println(user);
            ExistDBUsers existDBUser = new ExistDBUsers();
            existDBUser.setUsername(user);
            existDBUser.setGroups(Arrays.asList(existDbUserManagerServices.getUserGroups(details, user).split("\n")));
            existDBUser.setUmask(existDbUserManagerServices.getUserUmask(details, user));
            existDBUser.setPrimaryGroup(existDbUserManagerServices.getUserPrimaryGroup(details, user));
            existDBUser.setFullName(existDbUserManagerServices.getUserFullname(details, user));
            existDBUser.setDesc(existDbUserManagerServices.getUserDesc(details,user));
            existDBUser.setDefault(existDbUserManagerServices.isDeaultUser(user));
            existDBUsers.add(existDBUser);
        }
        return existDBUsers;
    }

    public ArrayList<ExistDBGroup> listGroups(){
        ExistDetails details = new ExistDetails();
        List<String> groups;
        ArrayList<ExistDBGroup> dbGroups = new ArrayList<>();
        groups = Arrays.asList(existDbGroupManagerServices.getGroups(details).split("\n"));

        for (String group: groups) {
            System.out.println(group);
            ExistDBGroup group1 = new ExistDBGroup();
            group1.setName(group);
            group1.setManager(existDbGroupManagerServices.getGroupManager(details, group));
            group1.setDesc(existDbGroupManagerServices.getGroupDesc(details, group));
            group1.setMembers(Arrays.asList(existDbGroupManagerServices.getGroupMembers(details, group).split("\n")));
            dbGroups.add(group1);
        }
        return dbGroups;
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
        System.out.println(details.toString());
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
