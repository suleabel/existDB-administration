package hu.sule.administration.controller;

import hu.sule.administration.model.ExistDBUser;
import hu.sule.administration.service.UserManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/exist/")
public class UserManagerController {

    @Autowired
    private UserManagerService userManagerService;

    @RequestMapping("/getUsers")
    public ArrayList<ExistDBUser> GetUsers(){
        return userManagerService.listUsers();
    }

    @RequestMapping("getUsersNames")
    public List<String> getUsersNames() {return userManagerService.getUsersNames();}

    @RequestMapping("/deleteUser")
    public String deleteUser(@RequestBody String username){ return userManagerService.deleteUser(username); }

    @RequestMapping("/createUser")
    public String createUser(@RequestBody ExistDBUser user){
        return userManagerService.createUser(user);
    }

    @RequestMapping("/editUser")
    public String editUser(@RequestBody ExistDBUser user) { return userManagerService.editUser(user); }

}
