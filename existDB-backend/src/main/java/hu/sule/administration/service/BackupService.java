package hu.sule.administration.service;

import hu.sule.administration.model.BackupEntity;
import hu.sule.administration.model.CreateBackupEntity;
import hu.sule.administration.queries.ExistDbBackupsAndRestoreQueries;
import org.eclipse.jetty.util.IO;
import org.exist.xquery.functions.session.Create;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class BackupService {

    @Autowired
    private ExistDbBackupsAndRestoreQueries existDbBackupsAndRestoreQueries;

    private static final Logger logger = LoggerFactory.getLogger(BackupService.class);

    public ArrayList<BackupEntity> getBackups(String url) throws IOException, JDOMException {
        return mapBackups(existDbBackupsAndRestoreQueries.getBackups(ExistDbCredentialsService.getDetails(), url));
    }

    public String createBackup(CreateBackupEntity createBackupEntity) throws JDOMException, IOException {
        return new XMLOutputter(Format.getPrettyFormat()).outputString(new SAXBuilder().build(new StringReader(existDbBackupsAndRestoreQueries.createBackup(ExistDbCredentialsService.getDetails(), createBackupEntity))));
    }

    public String restoreBackup(String name) throws JDOMException, IOException {
        return new XMLOutputter(Format.getPrettyFormat()).outputString(new SAXBuilder().build(new StringReader(existDbBackupsAndRestoreQueries.restoreBackup(ExistDbCredentialsService.getDetails(), name))));

    }

    private ArrayList<BackupEntity> mapBackups(String input) throws JDOMException, IOException {
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
