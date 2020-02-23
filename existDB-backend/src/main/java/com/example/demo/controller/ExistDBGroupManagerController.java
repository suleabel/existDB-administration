package com.example.demo.controller;

import com.example.demo.model.ExistDBGroup;
import com.example.demo.service.ExistDbMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping("/getGroupsNames")
    public List<String> getGroupsName() {
        return existDbMainService.getGroupsName();
    }

    @RequestMapping("/createGroup")
    public String createGroup(@RequestBody ExistDBGroup group) { return existDbMainService.createGroup(group);}

    @RequestMapping("/deleteGroup")
    public String deleteGroup(@RequestBody String groupName) {return existDbMainService.deleteGroup(groupName);}
}
