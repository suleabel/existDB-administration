package hu.sule.administration.controller;


import hu.sule.administration.model.BackupEntity;
import hu.sule.administration.model.CreateBackupEntity;
import hu.sule.administration.service.BackupService;
import org.eclipse.jetty.util.IO;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/backups/")
public class BackupsController {

    @Autowired
    private BackupService backupService;

    @RequestMapping("/getBackups")
    public ArrayList<BackupEntity> getBackups(HttpEntity<String> httpEntity) throws XMLDBException, IOException, JDOMException {
        return backupService.getBackups(httpEntity.getBody());
    }

    @RequestMapping("/createBackup")
    public String createBackup(@RequestBody CreateBackupEntity createBackupEntity) throws XMLDBException, JDOMException, IOException{
        return backupService.createBackup(createBackupEntity);
    }

    @RequestMapping("/restoreBackup")
    public ResponseEntity<String> restoreBackup(HttpEntity<String> httpEntity) throws XMLDBException, JDOMException, IOException{
        return new ResponseEntity<>(backupService.restoreBackup(httpEntity.getBody()), HttpStatus.OK);
    }

}
