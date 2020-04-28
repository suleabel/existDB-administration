package hu.sule.administration.service;

import hu.sule.administration.model.ExistDBUser;
import hu.sule.administration.model.ExistDetails;
import hu.sule.administration.queries.ExistDbUserManagerQueries;
import hu.sule.administration.service.impl.UserManagerServiceImpl;
import hu.sule.administration.util.Mappers;
import javafx.beans.binding.Bindings;
import org.jdom2.JDOMException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserManagerServiceImplTest {

    @Autowired
    private UserManagerServiceImpl userManagerServiceImpl;

    @MockBean
    private ExistDbUserManagerQueries existDbUserManagerQueries;

    private ExistDetails existDetails = new ExistDetails("admin","admin","127.0.0.1","127.0.0.1","/db");

//    @Test
//    public void listUsers() throws IOException, JDOMException {
//        Mockito.when(existDbUserManagerQueries.readUsers(existDetails)).thenReturn("<users><user><username>SYSTEM</username><primaryGroups>dba</primaryGroups><fullName>SYSTEM</fullName><groups><group>dba</group></groups><umask>18</umask><desc>System Internals</desc><isEnabled>true</isEnabled></user></users>");
//        Mockito.when(existDbUserManagerQueries.isDefaultUser("SYSTEM")).thenReturn(true);
//        ArrayList<ExistDBUser> result = userManagerServiceImpl.listUsers();
//        assertEquals(1,result.size());
//    }

//    @Test(expected = JDOMException.class)
//    public void listUsersNull() throws IOException, JDOMException {
//        Mockito.when(existDbUserManagerQueries.readUsers(existDetails)).thenReturn("");
//        ArrayList<ExistDBUser> result = userManagerService.listUsers();
//    }

    @Test
    public void createUser() {
    }

    @Test
    public void deleteUser() {
    }

    @Test
    public void editUser() {
    }

    @Test
    public void isAdmin() {
    }

    @Test
    public void getUsersNames() {
    }

    @Test
    public void editUserGroups() {
    }
}