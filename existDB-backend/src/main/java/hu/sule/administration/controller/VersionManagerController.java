package hu.sule.administration.controller;

import hu.sule.administration.service.VersionManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/version")
public class VersionManagerController {

    @Autowired
    private VersionManagerService versionManagerService;

    @RequestMapping("/isEnabled")
    public boolean versionManagerIsEnabled() {
        return versionManagerService.isEnabled();
    }

    @RequestMapping("/enableVersionManagement")
    public boolean enableVersionManagement() {
        return versionManagerService.enableVersionManager();
    }
}