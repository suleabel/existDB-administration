package hu.sule.administration.service;

import hu.sule.administration.model.ExistDBGroup;
import hu.sule.administration.model.ExistDBUser;
import hu.sule.administration.model.ExistDetails;
import hu.sule.administration.queries.ExistDbUserManagerQueries;
import hu.sule.administration.service.impl.UserManagerServiceImpl;
import hu.sule.administration.util.Mappers;
import org.jdom2.JDOMException;
import org.jdom2.input.JDOMParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserManagerServiceImplTest {

    @Autowired
    private UserManagerServiceImpl userManagerServiceImpl;

    @MockBean
    private ExistDbUserManagerQueries existDbUserManagerQueries;
    @Test
    public void listUsers() throws IOException, JDOMException {
        when(existDbUserManagerQueries.readUsers(any())).thenReturn("<users><user><username>SYSTEM</username><primaryGroups>dba</primaryGroups><fullName>SYSTEM</fullName><groups><group>dba</group></groups><umask>18</umask><desc>System Internals</desc><isEnabled>true</isEnabled></user></users>");
        when(existDbUserManagerQueries.isDefaultUser(any())).thenReturn(true);
        ArrayList<ExistDBUser> result = userManagerServiceImpl.listUsers();
        assertEquals(1,result.size());
    }

    @Test
    public void listUsersNoInput(){
        when(existDbUserManagerQueries.readUsers(any())).thenReturn("");
        Exception exception = assertThrows(JDOMParseException.class, () -> {
            userManagerServiceImpl.listUsers();
        });
        String expectedMessage = "Error on line -1: Premature end of file.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void createNotExistingUser() {
        when(existDbUserManagerQueries.createUser(any(),any())).thenReturn("User created!");
        assertEquals("User created!", userManagerServiceImpl.createUser(new ExistDBUser()));
    }

    @Test
    public void deleteUser() {
        when(existDbUserManagerQueries.deleteUser(any(),any())).thenReturn("User is not exist!");
        assertEquals("User is not exist!", userManagerServiceImpl.deleteUser("username"));
    }

    @Test
    public void editUser() {
    }

    @Test
    public void isAdmin() throws XMLDBException {
        when(existDbUserManagerQueries.isAdminAccess(any())).thenReturn(true);
        assertTrue(userManagerServiceImpl.isAdmin());
    }

    @Test
    public void getUsersNames() {
        List<String> users = new ArrayList<>();
        users.add("user1");
        users.add("user2");
        when(existDbUserManagerQueries.getUsersNames(any())).thenReturn("user1\nuser2");
        assertEquals(userManagerServiceImpl.getUsersNames(), users);
    }

    @Test
    public void editUserGroups()throws JDOMException, IOException {
//        List<String> groups = new ArrayList<>();
//        groups.add("dba1");
//        groups.add("dba2");
//        ExistDBUser user = new ExistDBUser("SYSTEM",groups,"18","dba","SYSTEM","System Internals","asd123",true,true);
//        when(existDbUserManagerQueries.readUsers(any())).thenReturn("<users><user><username>SYSTEM</username><primaryGroups>dba</primaryGroups><fullName>SYSTEM</fullName><groups><group>dba1</group><group>dba2</group></groups><umask>18</umask><desc>System Internals</desc><isEnabled>true</isEnabled></user></users>");
//        when(existDbUserManagerQueries.isDefaultUser(any())).thenReturn(true);
//        userManagerServiceImpl.editUserGroups(new ExistDBUser());

    }
}