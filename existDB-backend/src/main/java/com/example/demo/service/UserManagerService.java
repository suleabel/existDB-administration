package com.example.demo.service;

import com.example.demo.model.ExistDBUsers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserManagerService {

    @Autowired
    private ExistDbUserManagerQueries existDbUserManagerQueries;

    private static final Logger logger = LoggerFactory.getLogger(ExistDbCredentialsService.class);

    public ArrayList<ExistDBUsers> listUsers() {

        List<String> users;
        ArrayList<ExistDBUsers> existDBUserForCreates = new ArrayList<>();

        users = Arrays.asList(existDbUserManagerQueries.getUsers(ExistDbCredentialsService.getDetails()).split("\n"));

        for (String user: users) {
            ExistDBUsers existDBUser = new ExistDBUsers();
            existDBUser.setUsername(user);
            existDBUser.setGroups(Arrays.asList(existDbUserManagerQueries.getUserGroups(ExistDbCredentialsService.getDetails(), user).split("\n")));
            existDBUser.setUmask(existDbUserManagerQueries.getUserUmask(ExistDbCredentialsService.getDetails(), user));
            existDBUser.setPrimaryGroup(existDbUserManagerQueries.getUserPrimaryGroup(ExistDbCredentialsService.getDetails(), user));
            existDBUser.setFullName(existDbUserManagerQueries.getUserFullname(ExistDbCredentialsService.getDetails(), user));
            existDBUser.setDesc(existDbUserManagerQueries.getUserDesc(ExistDbCredentialsService.getDetails(), user));
            existDBUser.setDefault(existDbUserManagerQueries.isDefaultUser(user));
            existDBUser.setEnabled(existDbUserManagerQueries.isAccountEnabled(ExistDbCredentialsService.getDetails(), user));
            existDBUserForCreates.add(existDBUser);
        }
        logger.info("list users: " + existDBUserForCreates.toString());
        return existDBUserForCreates;
    }

    public String createUser(ExistDBUsers user){
        logger.info("try to create user:" + user.toString());
        return existDbUserManagerQueries.createUser(ExistDbCredentialsService.getDetails(), user);
    }

    public String deleteUser(String username){
        return existDbUserManagerQueries.deleteUser(ExistDbCredentialsService.getDetails(), username);
    }

    public String editUser(ExistDBUsers user) { return existDbUserManagerQueries.editUser(ExistDbCredentialsService.getDetails(), user); }

    public boolean isAdmin() throws Exception{
        return existDbUserManagerQueries.isAndminAccess(ExistDbCredentialsService.getDetails());
    }

    public List<String> getUsersNames() {
        return Arrays.asList(existDbUserManagerQueries.getUsers(ExistDbCredentialsService.getDetails()).split("\n"));
    }
}
