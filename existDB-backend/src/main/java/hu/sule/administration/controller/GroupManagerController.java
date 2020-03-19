package hu.sule.administration.controller;

import hu.sule.administration.model.ExistDBGroup;
import hu.sule.administration.service.GroupManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/exist/")
public class GroupManagerController {

    @Autowired
    private GroupManagerService groupManagerService;

    @RequestMapping("/getGroups")
    public ArrayList<ExistDBGroup> getGroups() {
        System.out.println("asdasd");
        return groupManagerService.listGroups();
    }

    @RequestMapping("/getGroupsNames")
    public List<String> getGroupsName() {
        return groupManagerService.getGroupsName();
    }

    @RequestMapping("/createGroup")
    public String createGroup(@RequestBody ExistDBGroup group) {
        return groupManagerService.createGroup(group);}

    @RequestMapping("/deleteGroup")
    public String deleteGroup(@RequestBody String groupName) {return groupManagerService.deleteGroup(groupName);}

    @RequestMapping("/editGroup")
    public String editGroup(@RequestBody ExistDBGroup group){
        return groupManagerService.editGroup(group);
    }
}
