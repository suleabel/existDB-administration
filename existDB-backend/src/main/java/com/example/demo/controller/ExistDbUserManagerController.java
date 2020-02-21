package com.example.demo.controller;

import com.example.demo.model.ExistDBUserForCreate;
import com.example.demo.model.ExistDBUsers;
import com.example.demo.service.ExistDbMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/exist/")
public class ExistDBUserManagerController {

    private ExistDbMainService existDbMainService;

    @Autowired
    public void setExistAuthenticationService(ExistDbMainService existDbMainService){
        this.existDbMainService = existDbMainService;
    }
    @RequestMapping(value = "/getusers", method = RequestMethod.GET)
    public ArrayList<ExistDBUsers> GetUsers(){
        return existDbMainService.listUsers();
    }

    @RequestMapping("/deleteuser")
    public void deleteUser(@RequestBody String username){
        //return existDbMainService.deleteUser(username);
        System.out.println("deletedUser: " + username);

    }

    @RequestMapping("/getrootcollection")
    public List<String> getRootCollection(){
        return existDbMainService.getCollection();
    }

    @RequestMapping("/createUser")
    public String createUser(@RequestBody ExistDBUserForCreate user){
        return existDbMainService.createUser(user);
    }


}
