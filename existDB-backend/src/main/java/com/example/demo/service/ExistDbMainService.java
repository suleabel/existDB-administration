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
    private ExistDbUserManagerQueries existDbUserManagerQueries;

    @Autowired
    private ExistDbCollectionManagerQueries existDbCollectionManagerQueries;

    @Autowired
    private ExistDbGroupManagerQueries existDbGroupManagerQueries;

    private static final Logger logger = LoggerFactory.getLogger(ExistDbMainService.class);

    private static ExistDetails details = new ExistDetails();

    public void initDatabaseDriver(String username, String password, String url) {
        logger.info("initializeDatabaseDriver");
        details.setUsername(username);
        details.setPassword(password);
        details.setUrl("xmldb:exist://" + url + "/exist/xmlrpc");
        util.initDatabaseDriver(details.getDriver());

    }
// user manager functions //

    public ArrayList<ExistDBUsers> listUsers() {

        List<String> users;
        ArrayList<ExistDBUsers> existDBUserForCreates = new ArrayList<>();

        users = Arrays.asList(existDbUserManagerQueries.getUsers(details).split("\n"));

        for (String user: users) {
            ExistDBUsers existDBUser = new ExistDBUsers();
            existDBUser.setUsername(user);
            existDBUser.setGroups(Arrays.asList(existDbUserManagerQueries.getUserGroups(details, user).split("\n")));
            existDBUser.setUmask(existDbUserManagerQueries.getUserUmask(details, user));
            existDBUser.setPrimaryGroup(existDbUserManagerQueries.getUserPrimaryGroup(details, user));
            existDBUser.setFullName(existDbUserManagerQueries.getUserFullname(details, user));
            existDBUser.setDesc(existDbUserManagerQueries.getUserDesc(details, user));
            existDBUser.setDefault(existDbUserManagerQueries.isDefaultUser(user));
            existDBUser.setEnabled(existDbUserManagerQueries.isAccountEnabled(details, user));
            existDBUserForCreates.add(existDBUser);
        }
        logger.info("list users: " + existDBUserForCreates.toString());
        return existDBUserForCreates;
    }

    public String createUser(ExistDBUsers user){
        logger.info("try to create user:" + user.toString());
        return existDbUserManagerQueries.createUser(details, user);
    }

    public String deleteUser(String username){
        return existDbUserManagerQueries.deleteUser(details, username);
    }

    public String editUser(ExistDBUsers user) { return existDbUserManagerQueries.editUser(details, user); }

    public boolean isAdmin (){
        return existDbUserManagerQueries.isAndminAccess(details);
    }

    public List<String> getUsersNames() {
        return Arrays.asList(existDbUserManagerQueries.getUsers(details).split("\n"));
    }

    // group manager functions //

    public ArrayList<ExistDBGroup> listGroups(){

        List<String> groups;
        ArrayList<ExistDBGroup> dbGroups = new ArrayList<>();
        groups = Arrays.asList(existDbGroupManagerQueries.getGroups(details).split("\n"));

        for (String group: groups) {
            ExistDBGroup group1 = new ExistDBGroup();
            group1.setGroupName(group);
            group1.setGroupManager(existDbGroupManagerQueries.getGroupManager(details, group));
            group1.setDesc(existDbGroupManagerQueries.getGroupDesc(details, group));
            group1.setGroupMembers(Arrays.asList(existDbGroupManagerQueries.getGroupMembers(details, group).split("\n")));
            group1.setDefault(existDbGroupManagerQueries.isDefaultGroup(group));
            dbGroups.add(group1);
        }
        return dbGroups;
    }

    public String createGroup(ExistDBGroup group) { return existDbGroupManagerQueries.createGroup(details, group); }

    public String deleteGroup(String group) { return existDbGroupManagerQueries.deleteGroup(details, group); }

    public String editGroup(ExistDBGroup group) { return existDbGroupManagerQueries.editGroup(details, group); }

    public List<String> getGroupsName() {
        return Arrays.asList(existDbGroupManagerQueries.getGroups(details).split("\n"));
    }

    // collection manager functions //

    public List<String> getCollection(String collection) {
        return Arrays.asList(existDbCollectionManagerQueries.getCollectionContent(details, collection).split("\n"));
    }

    public ArrayList<ExistFileManagerModel> getFileManagerContentByCollection(String collection) {
        System.out.println("collection: " + collection);
        ArrayList<ExistFileManagerModel> existFileManagerModels = new ArrayList<>();
        String[] collections = existDbCollectionManagerQueries.getCollectionContent(details, collection).split("\n");
        String[] resources = existDbCollectionManagerQueries.getCollectionResources(details, collection).split("\n");
        if(!collections[0].equals("")){
            for (String col: collections) {
                String[] ResData = existDbCollectionManagerQueries.getResourceData(details, collection, col).split("\n");
                ExistFileManagerModel existFileManagerModel = new ExistFileManagerModel(col, collection, ResData[0],ResData[1],ResData[2].equals("true"),ResData[3],"",false);
                existFileManagerModels.add(existFileManagerModel);
            }
        }
        if(!resources[0].equals("")){
            for (String res: resources){
                String[] ResData = existDbCollectionManagerQueries.getResourceData(details, collection, res).split("\n");
                ExistFileManagerModel existFileManagerModel = new ExistFileManagerModel(res, collection, ResData[0],ResData[1],ResData[2].equals("true"),ResData[3],ResData[4],true);
                System.out.println(existFileManagerModel.toString());
                existFileManagerModels.add(existFileManagerModel);
            }
        }
        return existFileManagerModels;
    }
    public String storeResource(ForStoreResource storeResource){
        return existDbCollectionManagerQueries.saveResource(details, storeResource);
    }

    public String deleteFile(ExistFileManagerModel existFileManagerModel) {
        return existDbCollectionManagerQueries.deleteResource(details, existFileManagerModel);
    }

    public String readBinaryFile(String resUrl){
        return existDbCollectionManagerQueries.readBinaryFile(details, resUrl);
    }



    public List<String> getCollectionFree(String collection){
        List<String> collectionTree = new ArrayList<>();
        collectionTree.add(collection);
        for (String col: getCollectionChilds(collection)) {
            collectionTree.addAll(getCollectionFree(col));
        }
        return collectionTree;
    }

    public List<String> getCollectionChilds(String col) {
        List<String> result = new ArrayList<>();
        for (String child: Arrays.asList(existDbCollectionManagerQueries.getCollectionContent(details, col).split("\n"))) {
            result.add(col+ "/" + child);
        }
        return result;
    }







    // spring security helper //

    public static String getDetailsPass() {
        return details.getPassword();
    }


}
