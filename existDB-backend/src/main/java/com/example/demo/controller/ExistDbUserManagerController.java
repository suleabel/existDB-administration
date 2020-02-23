package com.example.demo.controller;

import com.example.demo.model.ExistDBUsers;
import com.example.demo.service.ExistDbMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/exist/")
public class ExistDbUserManagerController {

    @Autowired
    private ExistDbMainService existDbMainService;

    @RequestMapping("/getUsers")
    public ArrayList<ExistDBUsers> GetUsers(){
        return existDbMainService.listUsers();
    }

    @RequestMapping("getUsersNames")
    public List<String> getUsersNames() {return existDbMainService.getUsersNames();}

    @RequestMapping("/deleteUser")
    public String deleteUser(@RequestBody String username){ return existDbMainService.deleteUser(username); }

    //collection managerbe kell majd
    @RequestMapping("/getrootcollection")
    public List<String> getRootCollection(){
        return existDbMainService.getCollection();
    }

    @RequestMapping("/createUser")
    public String createUser(@RequestBody ExistDBUsers user){
        return existDbMainService.createUser(user);
    }

    @RequestMapping("/editUser")
    public String editUser(@RequestBody ExistDBUsers user) { return existDbMainService.editUser(user); }

}
