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

    private VersionManagerService versionManagerServiceImpl;

    @Autowired
    public void setVersionManagerServiceImpl(VersionManagerService versionManagerServiceImpl) {
        this.versionManagerServiceImpl = versionManagerServiceImpl;
    }

    @RequestMapping("/isEnabled")
    public ResponseEntity<String> versionManagerIsEnabled() throws IOException {
        return new ResponseEntity<>("{\"response\":\"" + versionManagerServiceImpl.isEnabled() + "\"}", HttpStatus.OK);
    }

    @RequestMapping("/enableVersionManagement")
    public ResponseEntity<String> enableVersionManagement() {
        return new ResponseEntity<>("{\"response\":\"" + versionManagerServiceImpl.enableVersioning() + "\"}", HttpStatus.OK);
    }

    @RequestMapping("/getResHistory")
    public ResponseEntity<VersionsModel> getHistory(HttpEntity<String> httpEntity) throws JDOMException, IOException {
        return new ResponseEntity<>(versionManagerServiceImpl.getHistory(httpEntity.getBody()), HttpStatus.OK);
    }

    @RequestMapping("/getDiffByRev")
    public ResponseEntity<String> getDiffByRev(@RequestBody VersionByRevModel versionByRevModel) throws IOException, JDOMException{
        //direkt ilyen ne piszkáld
        return new ResponseEntity<>(versionManagerServiceImpl.getDiffByRev(versionByRevModel).replaceAll("\"", "'"), HttpStatus.OK);
    }

    @RequestMapping("/restoreResByRev")
    public ResponseEntity<String> restoreResByRev(@RequestBody VersionByRevModel versionByRevModel) throws IOException, JDOMException{
        System.out.println("/restoreResByRev");
        String result = versionManagerServiceImpl.restoreResByRev(versionByRevModel);
        System.out.println(result);
        return new ResponseEntity<>("{\"response\":\"" + result + "\"}", HttpStatus.OK);
    }
}
