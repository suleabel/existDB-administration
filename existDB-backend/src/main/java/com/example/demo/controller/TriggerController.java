package com.example.demo.controller;

import com.example.demo.model.EditTriggerModel;
import com.example.demo.model.ExistFileManagerModel;
import com.example.demo.service.TriggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/triggers/")
public class TriggerController {

    @Autowired
    TriggerService triggerService;

    @RequestMapping("initTriggersDefaultDir")
    public void initTriggerDirIfNotExist() {
        triggerService.InitTriggerDir();
    }

    @RequestMapping("initTriggerConfig")
    public String initTriggerConfig(HttpEntity<String> httpEntity){
        return triggerService.initTriggerConfig(httpEntity.getBody());
    }

    @RequestMapping("editTrigger")
    public String editTrigger(@RequestBody EditTriggerModel editTriggerModel){
        triggerService.addTriggerToConfiguration(editTriggerModel);
        return "success";
    }

    @RequestMapping("getTriggersConfig")
    public ArrayList<ExistFileManagerModel> getTriggerConfigurations(HttpEntity<String> httpEntity){
        return triggerService.getTriggerConfiguration(httpEntity.getBody());
    }


}
