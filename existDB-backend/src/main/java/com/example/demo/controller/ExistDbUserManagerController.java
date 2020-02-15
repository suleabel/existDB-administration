package com.example.demo.controller;

import com.example.demo.domain.ExistDBUsers;
import com.example.demo.service.ExistDbMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/exist/")
public class ExistDBUserManagerController {

    private ExistDbMainService existDbMainService;

    @Autowired
    public void setExistAuthenticationService(ExistDbMainService existDbMainService){
        this.existDbMainService = existDbMainService;
    }
    @RequestMapping("/getusers")
    public ArrayList<ExistDBUsers> GetUsers(){
        return existDbMainService.listUsers();
    }

}
