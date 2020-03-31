package hu.sule.administration.controller;

import hu.sule.administration.model.FileManagerEntity;
import hu.sule.administration.model.SerializeFile;
import hu.sule.administration.model.StoreDirOrFileModel;
import hu.sule.administration.service.FileExplorerService;
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
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/fileManager/")
public class FileManagerController {

    @Autowired
    private FileExplorerService fileExplorerService;

    @RequestMapping("/getDirectoryContent")
    public List<FileManagerEntity> getDirectoryContent(HttpEntity<String> httpEntity) throws XMLDBException {
        return fileExplorerService.getDirectoryContent(httpEntity.getBody());
    }

    @RequestMapping("/getRootDirectory")
    public String getRootDirectory() throws XMLDBException{
        return fileExplorerService.getRootDirectory();
    }

    @RequestMapping("/readFile")
    public String readFileContent(HttpEntity<String> httpEntity) throws XMLDBException, JDOMException, IOException {
        return fileExplorerService.getFileContent(httpEntity.getBody());
    }

    @RequestMapping("/makeDir")
    public ResponseEntity<String> makeDir(@RequestBody StoreDirOrFileModel storeDirOrFileModel) {
        return new ResponseEntity<>("{\"response\":\"" + fileExplorerService.makeDir(storeDirOrFileModel) + "\"}", HttpStatus.OK);
    }

    @RequestMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody StoreDirOrFileModel storeDirOrFileModel) {
        return new ResponseEntity<>("{\"response\":\"" +fileExplorerService.delete(storeDirOrFileModel) + "\"}", HttpStatus.OK);
    }

    @RequestMapping("/editFile")
    public ResponseEntity<String> editFile(@RequestBody SerializeFile file){
        return new ResponseEntity<>("{\"response\":\"" + fileExplorerService.editFile(file) + "\"}", HttpStatus.OK);
    }

    @RequestMapping("/serializeFile")
    public ResponseEntity<String> serializeFile(@RequestBody SerializeFile file){
        return new ResponseEntity<>("{\"response\":\"" + fileExplorerService.createFile(file) + "\"}", HttpStatus.OK);
    }
}


