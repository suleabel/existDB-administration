package hu.sule.administration.service.impl;

import hu.sule.administration.model.BackupEntity;
import hu.sule.administration.model.CreateBackupEntity;
import hu.sule.administration.queries.ExistDbBackupsAndRestoreQueries;
import hu.sule.administration.service.BackupService;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class BackupServiceImpl implements BackupService {

    @Autowired
    private ExistDbBackupsAndRestoreQueries existDbBackupsAndRestoreQueries;

    public ArrayList<BackupEntity> getBackups(String url) throws IOException, JDOMException {
        return mapBackups(existDbBackupsAndRestoreQueries.getBackups(ExistDbCredentialsServiceImpl.getDetails(), url));
    }

    public String createBackup(CreateBackupEntity createBackupEntity) throws JDOMException, IOException {
        return new XMLOutputter(Format.getPrettyFormat()).outputString(new SAXBuilder().build(new StringReader(existDbBackupsAndRestoreQueries.createBackup(ExistDbCredentialsServiceImpl.getDetails(), createBackupEntity))));
    }

    public String restoreBackup(String name) throws JDOMException, IOException {
        return new XMLOutputter(Format.getPrettyFormat()).outputString(new SAXBuilder().build(new StringReader(existDbBackupsAndRestoreQueries.restoreBackup(ExistDbCredentialsServiceImpl.getDetails(), name))));

    }

    public ArrayList<BackupEntity> mapBackups(String input) throws JDOMException, IOException {
        ArrayList<BackupEntity> backupEntities = new ArrayList<>();
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = null;
        doc = saxBuilder.build(new InputSource(new StringReader(input)));
        if(doc != null) {
            Element directory = doc.getRootElement();
            List<Element> backups = directory.getChildren();
            for (Element backup: backups) {
                List<Element> details = backup.getChildren();
                BackupEntity backupEntity = new BackupEntity();
                backupEntity.setFileName(backup.getAttributeValue("file"));
                backupEntity.setDownloadable(backupEntity.getFileName().contains(".zip"));
                for (Element detail: details) {
                    switch(detail.getName()){
                        case "nr-in-sequence":
                            backupEntity.setNrInSequence(detail.getValue());
                            break;
                        case "date":
                            backupEntity.setDate(detail.getValue());
                            break;
                        case "previous":
                            backupEntity.setPrevious(detail.getValue());
                            break;
                        case "incremental":
                            backupEntity.setIncremental(detail.getValue());
                            break;
                    }
                }
                backupEntities.add(backupEntity);
            }
        }
        return backupEntities;
    }
}
