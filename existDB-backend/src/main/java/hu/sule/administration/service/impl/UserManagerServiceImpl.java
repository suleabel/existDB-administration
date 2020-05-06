package hu.sule.administration.service.impl;

import hu.sule.administration.model.ExistDBUser;
import hu.sule.administration.queries.ExistDbUserManagerQueries;
import hu.sule.administration.service.UserManagerService;
import hu.sule.administration.util.Mappers;
import org.jdom2.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserManagerServiceImpl implements UserManagerService {

    @Autowired
    private ExistDbUserManagerQueries existDbUserManagerQueries;

    private static final Logger logger = LoggerFactory.getLogger(UserManagerServiceImpl.class);

    @Override
    public ArrayList<ExistDBUser> listUsers() throws JDOMException, IOException {
        ArrayList<ExistDBUser> users = Mappers.mapUsersQueryResult(existDbUserManagerQueries.readUsers(ExistDbCredentialsServiceImpl.getDetails()));
        for (ExistDBUser user: users) {
            user.setDefault(existDbUserManagerQueries.isDefaultUser(user.getUsername()));
        }
        return users;
    }

    @Override
    public String createUser(ExistDBUser user) {
        return existDbUserManagerQueries.createUser(ExistDbCredentialsServiceImpl.getDetails(), user);
    }

    @Override
    public String deleteUser(String username) {
        return existDbUserManagerQueries.deleteUser(ExistDbCredentialsServiceImpl.getDetails(), username);
    }

    @Override
    public String editUser(ExistDBUser user) throws JDOMException, IOException {
        editUserGroups(user);
        return existDbUserManagerQueries.editUser(ExistDbCredentialsServiceImpl.getDetails(), user);
    }

    @Override
    public boolean isAdmin() throws XMLDBException {
        return existDbUserManagerQueries.isAdminAccess(ExistDbCredentialsServiceImpl.getDetails());
    }

    @Override
    public List<String> getUsersNames() {
        return Arrays.asList(existDbUserManagerQueries.getUsersNames(ExistDbCredentialsServiceImpl.getDetails()).split("\n"));
    }

    @Override
    public void editUserGroups(ExistDBUser user)throws JDOMException, IOException {
        ArrayList<ExistDBUser> existDBUsers = listUsers();
        for (ExistDBUser existDBUser : existDBUsers) {
            if (existDBUser.getUsername().equals(user.getUsername())) {
                List<String> oldGroups = existDBUser.getGroups();
                oldGroups.remove(existDBUser.getPrimaryGroup());
                List<String> newGroups = user.getGroups();
                newGroups.remove(user.getPrimaryGroup());
                for (String group : oldGroups) {
                    existDbUserManagerQueries.removeUserFromGroup(ExistDbCredentialsServiceImpl.getDetails(), existDBUser.getUsername(), group);
                }
                for (String group : newGroups) {
                    existDbUserManagerQueries.addUserToGroup(ExistDbCredentialsServiceImpl.getDetails(), existDBUser.getUsername(), group);
                }
            }
        }
    }
}
