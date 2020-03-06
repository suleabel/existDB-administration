package hu.sule.administration.service;

import hu.sule.administration.model.ExistDBUser;
import hu.sule.administration.queries.ExistDbUserManagerQueries;
import hu.sule.administration.util.JaxbUnmarshaller;
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

    @Autowired
    private JaxbUnmarshaller jaxbUnmarshaller;

    private static final Logger logger = LoggerFactory.getLogger(ExistDbCredentialsService.class);

    public ArrayList<ExistDBUser> listUsers() {
        ArrayList<ExistDBUser> existDBUsers = jaxbUnmarshaller.unmarshallUsersAndDetails(existDbUserManagerQueries.getUsersData(ExistDbCredentialsService.getDetails()));
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

    public String editUser(ExistDBUser user) {
        editUserGroups(user);
        return existDbUserManagerQueries.editUser(ExistDbCredentialsService.getDetails(), user);
    }

    public boolean isAdmin() throws Exception{
        return existDbUserManagerQueries.isAdminAccess(ExistDbCredentialsService.getDetails());
    }

    public List<String> getUsersNames() {
        return Arrays.asList(existDbUserManagerQueries.getUsers(ExistDbCredentialsService.getDetails()).split("\n"));
    }

    private void editUserGroups(ExistDBUser user){
        ArrayList<ExistDBUser> existDBUsers = jaxbUnmarshaller.unmarshallUsersAndDetails(existDbUserManagerQueries.getUsersData(ExistDbCredentialsService.getDetails()));
        for (ExistDBUser existDBUser: existDBUsers) {
            if(existDBUser.getUsername().equals(user.getUsername())){
                List<String> oldGroups = existDBUser.getGroups();
                oldGroups.remove(existDBUser.getPrimaryGroup());
                List<String> newGroups = user.getGroups();
                newGroups.remove(user.getPrimaryGroup());
                for (String group: oldGroups) {
                    existDbUserManagerQueries.removeUserFromGroup(ExistDbCredentialsService.getDetails(), existDBUser.getUsername(), group);
                }
                for (String group: newGroups) {
                    existDbUserManagerQueries.addUserToGroup(ExistDbCredentialsService.getDetails(), existDBUser.getUsername(), group);
                }
            }
        }
    }

}
