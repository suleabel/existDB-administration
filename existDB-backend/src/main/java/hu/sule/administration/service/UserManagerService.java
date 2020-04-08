package hu.sule.administration.service;

import hu.sule.administration.model.ExistDBUser;
import org.jdom2.JDOMException;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public interface UserManagerService {
    ArrayList<ExistDBUser> listUsers() throws JDOMException, IOException;
    String createUser(ExistDBUser user);
    String deleteUser(String username);
    String editUser(ExistDBUser user) throws JDOMException, IOException;
    boolean isAdmin() throws XMLDBException;
    List<String> getUsersNames();
    void editUserGroups(ExistDBUser user)throws JDOMException, IOException;
}
