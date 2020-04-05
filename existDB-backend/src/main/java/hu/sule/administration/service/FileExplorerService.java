package hu.sule.administration.service;

import hu.sule.administration.model.FileManagerEntity;
import hu.sule.administration.model.SerializeFile;
import hu.sule.administration.model.StoreDirOrFileModel;
import org.jdom2.JDOMException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
@Service
public interface FileExplorerService {
    List<FileManagerEntity> getDirectoryContent(String dirname);
    String getRootDirectory();
    String getFileContent(String url) throws JDOMException, IOException;
    String makeDir(StoreDirOrFileModel storeDirOrFileModel);
    String delete(StoreDirOrFileModel storeDirOrFileModel);
    String createFile(SerializeFile file);
    String editFile(SerializeFile file);
}
