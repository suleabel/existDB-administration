package hu.sule.administration.controller;

import hu.sule.administration.model.FileManagerEntity;
import hu.sule.administration.service.FileExplorerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/fileManager/")
public class FileManagerController {

    @Autowired
    private FileExplorerService fileExplorerService;

    @RequestMapping("/getDirectoryContent")
    public List<FileManagerEntity> getDirectoryContent(HttpEntity<String> httpEntity){
        System.out.println("getDirContent");
        return fileExplorerService.getDirectoryContent(httpEntity.getBody());
    }

    @RequestMapping("/getRootDirectory")
    public String getRootDirectory() {
        System.out.println("getRootDir");
        return fileExplorerService.getRootDirectory();
    }

    @RequestMapping("/readFile")
    public String readFileContent(HttpEntity<String> httpEntity){
        return fileExplorerService.getFileContent(httpEntity.getBody());
    }
}


