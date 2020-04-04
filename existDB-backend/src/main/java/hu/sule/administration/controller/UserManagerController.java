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
    public ResponseEntity<ArrayList<ExistDBUser>> GetUsers() throws  JDOMException, IOException {
        return new ResponseEntity<>(userManagerService.listUsers(), HttpStatus.OK);
    }

    @RequestMapping("getUsersNames")
    public ResponseEntity<List<String>> getUsersNames() {return new ResponseEntity<>(userManagerService.getUsersNames(),HttpStatus.OK);}

    @RequestMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestBody String username) {
        return new ResponseEntity<>("{\"response\":\"" + userManagerService.deleteUser(username) + "\"}",HttpStatus.OK);
    }

    @RequestMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody ExistDBUser user) {
        return new ResponseEntity<>("{\"response\":\"" + userManagerService.createUser(user) + "\"}",HttpStatus.OK);
    }

    @RequestMapping("/editUser")
    public ResponseEntity<String> editUser(@RequestBody ExistDBUser user) throws JDOMException, IOException {
        return new ResponseEntity<>("{\"response\":\"" + userManagerService.editUser(user) + "\"}",HttpStatus.OK);
    }

}
