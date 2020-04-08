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

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/fileManager/")
public class FileManagerController {

    private FileExplorerService fileExplorerServiceImpl;

    @Autowired
    public void setFileExplorerServiceImpl(FileExplorerService fileExplorerServiceImpl) {
        this.fileExplorerServiceImpl = fileExplorerServiceImpl;
    }

    @RequestMapping("/getDirectoryContent")
    public List<FileManagerEntity> getDirectoryContent(HttpEntity<String> httpEntity) throws IOException, JDOMException{
        return fileExplorerServiceImpl.getDirectoryContent(httpEntity.getBody());
    }

    @RequestMapping("/getRootDirectory")
    public ResponseEntity<String> getRootDirectory() {
        return new ResponseEntity<>("{\"response\":\"" + fileExplorerServiceImpl.getRootDirectory() + "\"}",HttpStatus.OK);
    }

    @RequestMapping("/readFile")
    public ResponseEntity<String> readFileContent(HttpEntity<String> httpEntity) throws JDOMException, IOException {
        return new ResponseEntity<>("{\"response\":\"" + fileExplorerServiceImpl.getFileContent(httpEntity.getBody()) + "\"}",HttpStatus.OK);
    }

    @RequestMapping("/makeDir")
    public ResponseEntity<String> makeDir(@RequestBody StoreDirOrFileModel storeDirOrFileModel) {
        return new ResponseEntity<>("{\"response\":\"" + fileExplorerServiceImpl.makeDir(storeDirOrFileModel) + "\"}", HttpStatus.OK);
    }

    @RequestMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody StoreDirOrFileModel storeDirOrFileModel) {
        return new ResponseEntity<>("{\"response\":\"" + fileExplorerServiceImpl.delete(storeDirOrFileModel) + "\"}", HttpStatus.OK);
    }

    @RequestMapping("/editFile")
    public ResponseEntity<String> editFile(@RequestBody SerializeFile file){
        return new ResponseEntity<>("{\"response\":\"" + fileExplorerServiceImpl.editFile(file) + "\"}", HttpStatus.OK);
    }

    @RequestMapping("/serializeFile")
    public ResponseEntity<String> serializeFile(@RequestBody SerializeFile file){
        return new ResponseEntity<>("{\"response\":\"" + fileExplorerServiceImpl.createFile(file) + "\"}", HttpStatus.OK);
    }
}


