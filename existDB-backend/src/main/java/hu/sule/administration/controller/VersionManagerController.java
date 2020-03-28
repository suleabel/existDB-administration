package hu.sule.administration.controller;

import hu.sule.administration.service.VersionManagerService;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/version")
public class VersionManagerController {

    @Autowired
    private VersionManagerService versionManagerService;

    @RequestMapping("/isEnabled")
    public ResponseEntity<String> versionManagerIsEnabled() throws JDOMException, IOException {
        return new ResponseEntity<>(versionManagerService.isEnabled(), HttpStatus.OK);
    }

    @RequestMapping("/enableVersionManagement")
    public ResponseEntity<String> enableVersionManagement() throws XMLDBException, JDOMException, IOException {
        return new ResponseEntity<>(versionManagerService.enableVersioning(), HttpStatus.OK);
    }

    public ResponseEntity<String> getHistory(HttpEntity<String> httpEntity) throws JDOMException, IOException {
        return new ResponseEntity<>(versionManagerService.getHistory(httpEntity.getBody()), HttpStatus.OK);
    }
}
