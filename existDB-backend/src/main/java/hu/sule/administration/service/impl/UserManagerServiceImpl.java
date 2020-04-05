package hu.sule.administration.service.impl;

import hu.sule.administration.model.ExistDBUser;
import hu.sule.administration.queries.ExistDbUserManagerQueries;
import hu.sule.administration.service.UserManagerService;
import hu.sule.administration.service.impl.ExistDbCredentialsServiceImpl;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserManagerServiceImpl implements UserManagerService {

    @Autowired
    private ExistDbUserManagerQueries existDbUserManagerQueries;

    private static final Logger logger = LoggerFactory.getLogger(ExistDbCredentialsServiceImpl.class);

    @Override
    public ArrayList<ExistDBUser> listUsers() throws JDOMException, IOException {
        return mapUsersQueryResult(existDbUserManagerQueries.getUsersData(ExistDbCredentialsServiceImpl.getDetails()));
    }

    @Override
    public String createUser(ExistDBUser user) {
        logger.info("try to create user:" + user.toString());
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
        ArrayList<ExistDBUser> existDBUsers = mapUsersQueryResult(existDbUserManagerQueries.getUsersData(ExistDbCredentialsServiceImpl.getDetails()));
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

    @Override
    public ArrayList<ExistDBUser> mapUsersQueryResult(String input) throws JDOMException, IOException {
        ArrayList<ExistDBUser> existDBUsers = new ArrayList<>();
        ExistDBUser existDBUser;
        ArrayList<String> userGroups;
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = null;
        doc = saxBuilder.build(new InputSource(new StringReader(input)));
        if (doc != null) {
            Element result = doc.getRootElement();
            List<Element> users = result.getChildren();
            for (Element user : users) {
                userGroups = new ArrayList<>();
                existDBUser = new ExistDBUser(
                        user.getChildText("username"), user.getChildText("umask"), user.getChildText("primaryGroups"),
                        user.getChildText("fullName"), user.getChildText("desc"), existDbUserManagerQueries.isDefaultUser(user.getChildText("username")), true);
                Element groups = user.getChild("groups");
                List<Element> groupList = groups.getChildren();
                for (Element group : groupList) {
                    userGroups.add(group.getValue());
                }
                existDBUser.setGroups(userGroups);
                existDBUsers.add(existDBUser);
            }
        }
        return existDBUsers;
    }

}
