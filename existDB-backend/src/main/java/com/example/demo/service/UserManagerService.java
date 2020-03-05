package com.example.demo.service;

import com.example.demo.model.ExistDBUser;
import com.example.demo.model.ExistDBUsers;
import com.example.demo.queries.ExistDbUserManagerQueries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserManagerService {

    @Autowired
    private ExistDbUserManagerQueries existDbUserManagerQueries;

    private static final Logger logger = LoggerFactory.getLogger(ExistDbCredentialsService.class);

    public ArrayList<ExistDBUser> listUsers() {
        ArrayList<ExistDBUser> existDBUsers = getUsersAndDetails();
        for (ExistDBUser existDBUser: existDBUsers) {
            existDBUser.setDefault(existDbUserManagerQueries.isDefaultUser(existDBUser.getUsername()));
        }
//        List<String> users = Arrays.asList(existDbUserManagerQueries.getUsers(ExistDbCredentialsService.getDetails()).split("\n"));
//
//        for (String user: users) {
//            ExistDBUser existDBUser = new ExistDBUser();
//            existDBUser.setUsername(user);
//            existDBUser.setGroups(Arrays.asList(existDbUserManagerQueries.getUserGroups(ExistDbCredentialsService.getDetails(), user).split("\n")));
//            existDBUser.setUmask(existDbUserManagerQueries.getUserUmask(ExistDbCredentialsService.getDetails(), user));
//            existDBUser.setPrimaryGroup(existDbUserManagerQueries.getUserPrimaryGroup(ExistDbCredentialsService.getDetails(), user));
//            existDBUser.setFullName(existDbUserManagerQueries.getUserFullname(ExistDbCredentialsService.getDetails(), user));
//            existDBUser.setDesc(existDbUserManagerQueries.getUserDesc(ExistDbCredentialsService.getDetails(), user));
//            existDBUser.setDefault(existDbUserManagerQueries.isDefaultUser(user));
//            existDBUser.setEnabled(existDbUserManagerQueries.isAccountEnabled(ExistDbCredentialsService.getDetails(), user));
//            existDBUsers.add(existDBUser);
//        }
//        logger.info("list users: " + existDBUsers.toString());
        return existDBUsers;
    }

    public String createUser(ExistDBUser user){
        logger.info("try to create user:" + user.toString());
        return existDbUserManagerQueries.createUser(ExistDbCredentialsService.getDetails(), user);
    }

    public String deleteUser(String username){
        return existDbUserManagerQueries.deleteUser(ExistDbCredentialsService.getDetails(), username);
    }

    public String editUser(ExistDBUser user) { return existDbUserManagerQueries.editUser(ExistDbCredentialsService.getDetails(), user); }

    public boolean isAdmin() throws Exception{
        return existDbUserManagerQueries.isAdminAccess(ExistDbCredentialsService.getDetails());
    }

    public List<String> getUsersNames() {
        return Arrays.asList(existDbUserManagerQueries.getUsers(ExistDbCredentialsService.getDetails()).split("\n"));
    }

    private ArrayList<ExistDBUser> getUsersAndDetails() {
        System.out.println(existDbUserManagerQueries.getUsersData(ExistDbCredentialsService.getDetails()));
        ArrayList<ExistDBUser> existDBUserss = new ArrayList<>();
        JAXBContext jaxbContext;
        try{
            jaxbContext = JAXBContext.newInstance(ExistDBUsers.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ExistDBUsers existDBUsers = (ExistDBUsers) jaxbUnmarshaller.unmarshal(new StringReader(existDbUserManagerQueries.getUsersData(ExistDbCredentialsService.getDetails())));
            existDBUserss.addAll(existDBUsers.getExistDBUserList());
        } catch(JAXBException jaxbe) {
            logger.error("JAXBException: " + jaxbe);
        }
        return existDBUserss;
    }
}
