package hu.sule.administration.controller;

import hu.sule.administration.model.ExistDBGroup;
import hu.sule.administration.service.GroupManagerService;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/groups/")
public class GroupManagerController {

    @Autowired
    private GroupManagerService groupManagerService;

    @RequestMapping("/getGroups")
    public ResponseEntity<ArrayList<ExistDBGroup>> getGroups() throws JDOMException, IOException {
        return new ResponseEntity<>(groupManagerService.listGroups(),HttpStatus.OK);
    }

    @RequestMapping("/getGroupsNames")
    public ResponseEntity<List<String>> getGroupsName() {
        return new ResponseEntity<>(groupManagerService.getGroupsName(), HttpStatus.OK);
    }

    @RequestMapping("/createGroup")
    public ResponseEntity<String> createGroup(@RequestBody ExistDBGroup group)  {
        return new ResponseEntity<>("{\"response\":\"" + groupManagerService.createGroup(group) + "\"}", HttpStatus.OK);
    }

    @RequestMapping("/deleteGroup")
    public ResponseEntity<String> deleteGroup(@RequestBody String groupName) {
        return new ResponseEntity<>("{\"response\":\"" + groupManagerService.deleteGroup(groupName) + "\"}",HttpStatus.OK);
    }

    @RequestMapping("/editGroup")
    public ResponseEntity<String> editGroup(@RequestBody ExistDBGroup group) {
        return new ResponseEntity<>("{\"response\":\"" + groupManagerService.editGroup(group) + "\"}",HttpStatus.OK);
    }
}
