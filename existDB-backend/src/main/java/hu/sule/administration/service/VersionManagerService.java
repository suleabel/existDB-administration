package hu.sule.administration.service;

import hu.sule.administration.model.VersionByRevModel;
import hu.sule.administration.model.VersionsModel;
import org.jdom2.JDOMException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface VersionManagerService {
    String isEnabled() throws IOException;
    String enableVersioning();
    VersionsModel getHistory(String path) throws JDOMException, IOException;
    String getDiffByRev(VersionByRevModel versionByRevModel) throws IOException, JDOMException;
    String restoreResByRev(VersionByRevModel versionByRevModel) throws IOException, JDOMException;
}
