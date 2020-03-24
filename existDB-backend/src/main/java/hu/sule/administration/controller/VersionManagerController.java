package hu.sule.administration.controller;

import hu.sule.administration.service.VersionManagerService;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
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
    public boolean versionManagerIsEnabled() throws XMLDBException, JDOMException, IOException {
        return versionManagerService.isEnabled();
    }

    @RequestMapping("/enableVersionManagement")
    public String enableVersionManagement() throws XMLDBException, JDOMException, IOException {
        return versionManagerService.enableVersioning();
    }
}
