package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ExistDbMainService.class);

    private static ExistDetails details = new ExistDetails();

    public void initDatabaseDriver(String username, String password, String url) {
        logger.info("initializeDatabaseDriver");
        details.setUsername(username);
        details.setPassword(password);
        details.setUrl("xmldb:exist://" + url + "/exist/xmlrpc");
        util.initDatabaseDriver(details.getDriver());

    }

    public ArrayList<ExistDBUsersForList> listUsers() {
        List<String> users;
        ArrayList<ExistDBUsersForList> existDBUserForCreates = new ArrayList<>();

        users = Arrays.asList(existDbUserManagerServices.getUsers(details).split("\n"));

        for (String user: users) {
            ExistDBUsersForList existDBUser = new ExistDBUsersForList();
            existDBUser.setUsername(user);
            existDBUser.setGroups(Arrays.asList(existDbUserManagerServices.getUserGroups(details, user).split("\n")));
            existDBUser.setUmask(existDbUserManagerServices.getUserUmask(details, user));
            existDBUser.setPrimaryGroup(existDbUserManagerServices.getUserPrimaryGroup(details, user));
            existDBUser.setFullName(existDbUserManagerServices.getUserFullname(details, user));
            existDBUser.setDesc(existDbUserManagerServices.getUserDesc(details,user));
            existDBUser.setDefault(existDbUserManagerServices.isDefaultUser(user));
            existDBUserForCreates.add(existDBUser);
        }
        return existDBUserForCreates;
    }

    public ArrayList<ExistDBGroupForList> listGroups(){

        List<String> groups;
        ArrayList<ExistDBGroupForList> dbGroups = new ArrayList<>();
        groups = Arrays.asList(existDbGroupManagerServices.getGroups(details).split("\n"));

        for (String group: groups) {
            ExistDBGroupForList group1 = new ExistDBGroupForList();
            group1.setName(group);
            group1.setManager(existDbGroupManagerServices.getGroupManager(details, group));
            group1.setDesc(existDbGroupManagerServices.getGroupDesc(details, group));
            group1.setMembers(Arrays.asList(existDbGroupManagerServices.getGroupMembers(details, group).split("\n")));
            group1.setDefault(existDbGroupManagerServices.isDefaultGroup(group));
            dbGroups.add(group1);
        }
        return dbGroups;
    }

    public String createUser(ExistDBUserForCreate user){ return existDbUserManagerServices.createUser(details, user); }

    public String deleteUser(String username){
        return existDbUserManagerServices.deleteUser(details, username);
    }

    public String createGroup(ExistDBGroupForCreate group) { return existDbGroupManagerServices.createGroup(details, group); }

    public String deleteGroup(String group) { return existDbGroupManagerServices.deleteGroup(details, group); }


    public boolean isAdmin (){
        return existDbUserManagerServices.isAndminAccess(details);
    }

    public List<String> getCollection() {
        return Arrays.asList(existDbCollectionManagerService.getCollectionContent(details, "").split("\n"));
    }

    public List<String> getGroupsName() {
        return Arrays.asList(existDbGroupManagerServices.getGroups(details).split("\n"));
    }

    public List<String> getUsersNames() {
        return Arrays.asList(existDbUserManagerServices.getUsers(details).split("\n"));
    }










    public static String getDetailsPass() {
        return details.getPassword();
    }

}
