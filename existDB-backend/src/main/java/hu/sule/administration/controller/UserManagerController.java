package hu.sule.administration.controller;

import hu.sule.administration.model.ExistDBUser;
import hu.sule.administration.service.UserManagerService;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/exist/")
public class UserManagerController {

    @Autowired
    private UserManagerService userManagerService;

    @RequestMapping("/getUsers")
    public ResponseEntity<ArrayList<ExistDBUser>> GetUsers() throws XMLDBException, JDOMException, IOException {
        return new ResponseEntity<>(userManagerService.listUsers(), HttpStatus.OK);
    }

    @RequestMapping("getUsersNames")
    public List<String> getUsersNames() throws XMLDBException {return userManagerService.getUsersNames();}

    @RequestMapping("/deleteUser")
    public String deleteUser(@RequestBody String username) throws XMLDBException{ return userManagerService.deleteUser(username); }

    @RequestMapping("/createUser")
    public String createUser(@RequestBody ExistDBUser user) throws XMLDBException{
        return userManagerService.createUser(user);
    }

    @RequestMapping("/editUser")
    public String editUser(@RequestBody ExistDBUser user) throws XMLDBException, JDOMException, IOException { return userManagerService.editUser(user); }

}
