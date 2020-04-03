package hu.sule.administration.controller;

import hu.sule.administration.model.EditTriggerModel;
import hu.sule.administration.model.ExistCollectionManagerModel;
import hu.sule.administration.model.ForStoreResourceAndColl;
import hu.sule.administration.service.TriggerService;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/triggers/")
public class TriggerController {

    @Autowired
    TriggerService triggerService;

    @RequestMapping("initTriggersDefaultDir")
    public void initTriggerDirIfNotExist() throws XMLDBException {
        triggerService.InitTriggerDir();
    }

    @RequestMapping("initTriggerConfig")
    public String initTriggerConfig(HttpEntity<String> httpEntity) throws XMLDBException{
        return triggerService.initTriggerConfig(httpEntity.getBody());
    }

    @RequestMapping("addTrigger")
    public String addTrigger(@RequestBody EditTriggerModel editTriggerModel) throws XMLDBException {
        return triggerService.addTrigger(editTriggerModel);
    }

    @RequestMapping("editTrigger")
    public String saveEdit(@RequestBody ForStoreResourceAndColl storeResource) throws XMLDBException{
        return triggerService.editTrigger(storeResource);
    }

//    @RequestMapping("getTriggers")
//    public List<TriggerModel> getTriggers(HttpEntity<String> httpEntity){
//        return triggerService.getTriggers(httpEntity.getBody());
//    }

    @RequestMapping("getTriggersConfig")
    public ArrayList<ExistCollectionManagerModel> getTriggerConfigurations(HttpEntity<String> httpEntity) throws XMLDBException, JDOMException, IOException{
        return triggerService.getTriggerConfiguration(httpEntity.getBody());
    }


}
