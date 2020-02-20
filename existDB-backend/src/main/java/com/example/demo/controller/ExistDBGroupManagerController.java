package com.example.demo.controller;

import com.example.demo.model.ExistDBGroup;
import com.example.demo.service.ExistDbMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/exist/")
public class ExistDBGroupManagerController {

    @Autowired
    private ExistDbMainService existDbMainService;

    @RequestMapping("/getGroups")
    public ArrayList<ExistDBGroup> getGroups() {
        return existDbMainService.listGroups();
    }
}
