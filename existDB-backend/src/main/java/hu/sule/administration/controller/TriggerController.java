package hu.sule.administration.controller;

import hu.sule.administration.model.EditTriggerModel;
import hu.sule.administration.model.ExistCollectionManagerModel;
import hu.sule.administration.model.ForStoreResourceAndColl;
import hu.sule.administration.service.TriggerService;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/triggers/")
public class TriggerController {

    private TriggerService triggerServiceImpl;

    @Autowired
    public void setTriggerServiceImpl(TriggerService triggerServiceImpl) {
        this.triggerServiceImpl = triggerServiceImpl;
    }

    @RequestMapping("initTriggersDefaultDir")
    public void initTriggerDirIfNotExist() {
        triggerServiceImpl.InitTriggerDir();
    }

    @RequestMapping("initTriggerConfig")
    public ResponseEntity<String> initTriggerConfig(HttpEntity<String> httpEntity) {
        return new ResponseEntity<>("{\"response\":\"" + triggerServiceImpl.initTriggerConfig(httpEntity.getBody()) + "\"}", HttpStatus.OK);
    }

    @RequestMapping("addTrigger")
    public ResponseEntity<String> addTrigger(@RequestBody EditTriggerModel editTriggerModel) {
        return new ResponseEntity<>("{\"response\":\"" + triggerServiceImpl.addTrigger(editTriggerModel) + "\"}", HttpStatus.OK);
    }

    @RequestMapping("editTrigger")
    public ResponseEntity<String> saveEdit(@RequestBody ForStoreResourceAndColl storeResource){
        return new ResponseEntity<>("{\"response\":\"" + triggerServiceImpl.editTrigger(storeResource) + "\"}",HttpStatus.OK);
    }

    @RequestMapping("getTriggersConfig")
    public ResponseEntity<ArrayList<ExistCollectionManagerModel>> getTriggerConfigurations(HttpEntity<String> httpEntity) throws IOException, JDOMException {
        return new ResponseEntity<>(triggerServiceImpl.getTriggerConfiguration(httpEntity.getBody()),HttpStatus.OK);
    }


}
