package hu.sule.administration.controller;

import hu.sule.administration.model.ExistDBGroup;
import hu.sule.administration.service.GroupManagerService;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/exist/")
public class GroupManagerController {

    @Autowired
    private GroupManagerService groupManagerService;

    @RequestMapping("/getGroups")
    public ArrayList<ExistDBGroup> getGroups() throws XMLDBException, JDOMException, IOException {
        System.out.println("asdasd");
        return groupManagerService.listGroups();
    }

    @RequestMapping("/getGroupsNames")
    public List<String> getGroupsName() throws XMLDBException {
        return groupManagerService.getGroupsName();
    }

    @RequestMapping("/createGroup")
    public String createGroup(@RequestBody ExistDBGroup group) throws XMLDBException {
        return groupManagerService.createGroup(group);}

    @RequestMapping("/deleteGroup")
    public String deleteGroup(@RequestBody String groupName) throws XMLDBException {return groupManagerService.deleteGroup(groupName);}

    @RequestMapping("/editGroup")
    public String editGroup(@RequestBody ExistDBGroup group) throws XMLDBException{
        return groupManagerService.editGroup(group);
    }
}
