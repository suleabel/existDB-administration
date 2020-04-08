package hu.sule.administration.service;

import hu.sule.administration.model.BackupEntity;
import hu.sule.administration.model.CreateBackupEntity;
import org.jdom2.JDOMException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
@Service
public interface BackupService {

    ArrayList<BackupEntity> getBackups(String url) throws IOException, JDOMException;
    String createBackup(CreateBackupEntity createBackupEntity) throws JDOMException, IOException;
    String restoreBackup(String name) throws JDOMException, IOException;
}
