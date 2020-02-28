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

    public ArrayList<ExistDBUsers> listUsers() {

        List<String> users;
        ArrayList<ExistDBUsers> existDBUserForCreates = new ArrayList<>();

        users = Arrays.asList(existDbUserManagerServices.getUsers(details).split("\n"));

        for (String user: users) {
            ExistDBUsers existDBUser = new ExistDBUsers();
            existDBUser.setUsername(user);
            existDBUser.setGroups(Arrays.asList(existDbUserManagerServices.getUserGroups(details, user).split("\n")));
            existDBUser.setUmask(existDbUserManagerServices.getUserUmask(details, user));
            existDBUser.setPrimaryGroup(existDbUserManagerServices.getUserPrimaryGroup(details, user));
            existDBUser.setFullName(existDbUserManagerServices.getUserFullname(details, user));
            existDBUser.setDesc(existDbUserManagerServices.getUserDesc(details, user));
            existDBUser.setDefault(existDbUserManagerServices.isDefaultUser(user));
            existDBUser.setEnabled(existDbUserManagerServices.isAccountEnabled(details, user));
            existDBUserForCreates.add(existDBUser);
        }
        logger.info("list users: " + existDBUserForCreates.toString());
        return existDBUserForCreates;
    }

    public ArrayList<ExistDBGroup> listGroups(){

        List<String> groups;
        ArrayList<ExistDBGroup> dbGroups = new ArrayList<>();
        groups = Arrays.asList(existDbGroupManagerServices.getGroups(details).split("\n"));

        for (String group: groups) {
            ExistDBGroup group1 = new ExistDBGroup();
            group1.setName(group);
            group1.setManager(existDbGroupManagerServices.getGroupManager(details, group));
            group1.setDesc(existDbGroupManagerServices.getGroupDesc(details, group));
            group1.setMembers(Arrays.asList(existDbGroupManagerServices.getGroupMembers(details, group).split("\n")));
            group1.setDefault(existDbGroupManagerServices.isDefaultGroup(group));
            dbGroups.add(group1);
        }
        return dbGroups;
    }

    public String createUser(ExistDBUsers user){
        logger.info("try to create user:" + user.toString());
        return existDbUserManagerServices.createUser(details, user);
    }

    public String deleteUser(String username){
        return existDbUserManagerServices.deleteUser(details, username);
    }

    public String createGroup(ExistDBGroup group) { return existDbGroupManagerServices.createGroup(details, group); }

    public String deleteGroup(String group) { return existDbGroupManagerServices.deleteGroup(details, group); }

    public String editUser(ExistDBUsers user) { return existDbUserManagerServices.editUser(details, user); }

    public boolean isAdmin (){
        return existDbUserManagerServices.isAndminAccess(details);
    }

    public List<String> getCollection(String collection) {
        return Arrays.asList(existDbCollectionManagerService.getCollectionContent(details, collection).split("\n"));
    }

    public ArrayList<ExistFileManagerModel> getFileManagerContentByCollection(String collection) {
        ArrayList<ExistFileManagerModel> existFileManagerModels = new ArrayList<>();
        String[] collections = existDbCollectionManagerService.getCollectionContent(details, collection).split("\n");
        String[] resources = existDbCollectionManagerService.getCollectionResources(details, collection).split("\n");
        if(!collections[0].equals("")){
            for (String col: collections) {
                ExistFileManagerModel existFileManagerModel = new ExistFileManagerModel(col,false);
                existFileManagerModels.add(existFileManagerModel);
            }
        }
        if(!resources[0].equals("")){
            for (String res: resources){
                ExistFileManagerModel existFileManagerModel = new ExistFileManagerModel(res, true);
                existFileManagerModels.add(existFileManagerModel);
            }
        }
        return existFileManagerModels;
    }

    public List<String> getGroupsName() {
        return Arrays.asList(existDbGroupManagerServices.getGroups(details).split("\n"));
    }

    public List<String> getUsersNames() {
        return Arrays.asList(existDbUserManagerServices.getUsers(details).split("\n"));
    }

    public String storeResource(ForStoreResource storeResource){
        return existDbCollectionManagerService.saveResource(details, storeResource);
    }









    public static String getDetailsPass() {
        return details.getPassword();
    }


}
