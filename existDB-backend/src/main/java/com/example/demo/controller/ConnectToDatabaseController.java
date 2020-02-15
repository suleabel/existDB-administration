package com.example.demo.controller;

import com.example.demo.service.ExistDbMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exist")
public class ConnectToDatabaseController {

    private ExistDbMainService existDbMainService;

    @Autowired
    public void setExistAuthenticationService(ExistDbMainService existDbMainService){
        this.existDbMainService = existDbMainService;
    }
    @RequestMapping("/getusers")
    public void GetUsers(){
        existDbMainService.listUsers();
    }

}
