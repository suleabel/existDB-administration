package hu.sule.administration.service;

import hu.sule.administration.model.ExistDBUser;
import hu.sule.administration.queries.ExistDbUserManagerQueries;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import java.io.IOException;
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
        return mapUsersQueryResult(existDbUserManagerQueries.getUsersData(ExistDbCredentialsService.getDetails()));
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
        return Arrays.asList(existDbUserManagerQueries.getUsersNames(ExistDbCredentialsService.getDetails()).split("\n"));
    }

    private void editUserGroups(ExistDBUser user){
        ArrayList<ExistDBUser> existDBUsers = mapUsersQueryResult(existDbUserManagerQueries.getUsersData(ExistDbCredentialsService.getDetails()));
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

    private ArrayList<ExistDBUser> mapUsersQueryResult(String input){
        ArrayList<ExistDBUser> existDBUsers = new ArrayList<>();
        ExistDBUser existDBUser;
        ArrayList<String> userGroups;
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = null;
        try {
            doc = saxBuilder.build(new InputSource(new StringReader(input)));
        } catch (JDOMException | IOException e){
            logger.error("SAXBuilder exception: " + e.getMessage()) ;
        }
        if(doc != null) {
            Element result = doc.getRootElement();
            List<Element> users = result.getChildren();
            for (Element user: users) {
                userGroups = new ArrayList<>();
                existDBUser = new ExistDBUser(
                        user.getChildText("username"),user.getChildText("umask"),user.getChildText("primaryGroups"),
                        user.getChildText("fullName"),user.getChildText("desc"),existDbUserManagerQueries.isDefaultUser(user.getChildText("username")), true);
                Element groups = user.getChild("groups");
                List<Element> groupList = groups.getChildren();
                for(Element group: groupList){
                    userGroups.add(group.getValue());
                }
                existDBUser.setGroups(userGroups);
                existDBUsers.add(existDBUser);
            }
        }
        return existDBUsers;
    }

}
