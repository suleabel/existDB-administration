package hu.sule.administration.service.impl;

import hu.sule.administration.model.BackupEntity;
import hu.sule.administration.model.CreateBackupEntity;
import hu.sule.administration.queries.ExistDbBackupsAndRestoreQueries;
import hu.sule.administration.service.BackupService;
import hu.sule.administration.util.Mappers;
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
        return Mappers.mapBackups(existDbBackupsAndRestoreQueries.getBackups(ExistDbCredentialsServiceImpl.getDetails(), url));
    }

    public String createBackup(CreateBackupEntity createBackupEntity) throws JDOMException, IOException {
        return new XMLOutputter(Format.getPrettyFormat()).outputString(new SAXBuilder().build(new StringReader(existDbBackupsAndRestoreQueries.createBackup(ExistDbCredentialsServiceImpl.getDetails(), createBackupEntity))));
    }

    public String restoreBackup(String name) throws JDOMException, IOException {
        return new XMLOutputter(Format.getPrettyFormat()).outputString(new SAXBuilder().build(new StringReader(existDbBackupsAndRestoreQueries.restoreBackup(ExistDbCredentialsServiceImpl.getDetails(), name))));

    }
}
