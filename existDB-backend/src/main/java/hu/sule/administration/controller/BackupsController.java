package hu.sule.administration.controller;


import hu.sule.administration.model.BackupEntity;
import hu.sule.administration.model.CreateBackupEntity;
import hu.sule.administration.model.ResourceReadModel;
import hu.sule.administration.service.BackupService;
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
@RequestMapping("/backups/")
public class BackupsController {


    private BackupService backupServiceImpl;

    @Autowired
    public void setBackupServiceImpl(BackupService backupServiceImpl) {
        this.backupServiceImpl = backupServiceImpl;
    }

    @RequestMapping("/getBackups")
    public ResponseEntity<ArrayList<BackupEntity>> getBackups(HttpEntity<String> httpEntity) throws IOException, JDOMException {
        return new ResponseEntity<>(backupServiceImpl.getBackups(httpEntity.getBody()),HttpStatus.OK);
    }

    @RequestMapping("/createBackup")
    public ResponseEntity<ResourceReadModel> createBackup(@RequestBody CreateBackupEntity createBackupEntity) throws JDOMException, IOException{
        return new ResponseEntity<>(new ResourceReadModel(backupServiceImpl.createBackup(createBackupEntity), false),HttpStatus.OK);
    }

    @RequestMapping("/restoreBackup")
    public ResponseEntity<ResourceReadModel> restoreBackup(HttpEntity<String> httpEntity) throws JDOMException, IOException{
        return new ResponseEntity<>(new ResourceReadModel(backupServiceImpl.restoreBackup(httpEntity.getBody()),false), HttpStatus.OK);
    }

}
