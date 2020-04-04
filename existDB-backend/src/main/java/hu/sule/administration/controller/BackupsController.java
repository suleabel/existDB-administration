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
    public ResponseEntity<ArrayList<BackupEntity>> getBackups(HttpEntity<String> httpEntity) throws IOException, JDOMException {
        return new ResponseEntity<>(backupService.getBackups(httpEntity.getBody()),HttpStatus.OK);
    }

    @RequestMapping("/createBackup")
    public ResponseEntity<String> createBackup(@RequestBody CreateBackupEntity createBackupEntity) throws JDOMException, IOException{
        return new ResponseEntity<>("{\"response\":\"" + backupService.createBackup(createBackupEntity) + "\"}",HttpStatus.OK);
    }

    @RequestMapping("/restoreBackup")
    public ResponseEntity<String> restoreBackup(HttpEntity<String> httpEntity) throws JDOMException, IOException{
        return new ResponseEntity<>("{\"response\":\"" + backupService.restoreBackup(httpEntity.getBody()) + "\"}", HttpStatus.OK);
    }

}
