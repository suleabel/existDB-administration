package hu.sule.administration.controller;


import hu.sule.administration.model.BackupEntity;
import hu.sule.administration.model.CreateBackupEntity;
import hu.sule.administration.service.BackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/backups/")
public class BackupsController {

    @Autowired
    private BackupService backupService;

    @RequestMapping("/getBackups")
    public ArrayList<BackupEntity> getBackups(HttpEntity<String> httpEntity) {
        return backupService.getBackups(httpEntity.getBody());
    }

    @RequestMapping("/createBackup")
    public String createBackup(@RequestBody CreateBackupEntity createBackupEntity){
        System.out.println(createBackupEntity.toString());
        return backupService.createBackup(createBackupEntity);
    }

}
