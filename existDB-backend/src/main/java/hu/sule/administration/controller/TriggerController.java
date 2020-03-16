package hu.sule.administration.controller;

import hu.sule.administration.model.EditTriggerModel;
import hu.sule.administration.model.ExistCollectionManagerModel;
import hu.sule.administration.service.TriggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
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
        return triggerService.addTrigger(editTriggerModel);
    }

//    @RequestMapping("getTriggers")
//    public List<TriggerModel> getTriggers(HttpEntity<String> httpEntity){
//        return triggerService.getTriggers(httpEntity.getBody());
//    }

    @RequestMapping("getTriggersConfig")
    public ArrayList<ExistCollectionManagerModel> getTriggerConfigurations(HttpEntity<String> httpEntity){
        return triggerService.getTriggerConfiguration(httpEntity.getBody());
    }


}
