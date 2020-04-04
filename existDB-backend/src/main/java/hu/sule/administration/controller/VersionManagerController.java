package hu.sule.administration.controller;

import hu.sule.administration.model.VersionByRevModel;
import hu.sule.administration.model.VersionsModel;
import hu.sule.administration.service.VersionManagerService;
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

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/version")
public class VersionManagerController {

    @Autowired
    private VersionManagerService versionManagerService;

    @RequestMapping("/isEnabled")
    public ResponseEntity<String> versionManagerIsEnabled() throws IOException {
        return new ResponseEntity<>(versionManagerService.isEnabled(), HttpStatus.OK);
    }

    @RequestMapping("/enableVersionManagement")
    public ResponseEntity<String> enableVersionManagement() {
        return new ResponseEntity<>(versionManagerService.enableVersioning(), HttpStatus.OK);
    }

    @RequestMapping("/getResHistory")
    public ResponseEntity<VersionsModel> getHistory(HttpEntity<String> httpEntity) throws JDOMException, IOException {
        return new ResponseEntity<>(versionManagerService.getHistory(httpEntity.getBody()), HttpStatus.OK);
    }

    @RequestMapping("/getDiffByRev")
    public ResponseEntity<String> getDiffByRev(@RequestBody VersionByRevModel versionByRevModel) throws IOException, JDOMException{
        return new ResponseEntity<>(versionManagerService.getDiffByRev(versionByRevModel), HttpStatus.OK);
    }

    @RequestMapping("/restoreResByRev")
    public ResponseEntity<String> restoreResByRev(@RequestBody VersionByRevModel versionByRevModel) throws IOException, JDOMException{
        return new ResponseEntity<>(versionManagerService.resotreResByRev(versionByRevModel), HttpStatus.OK);
    }
}
