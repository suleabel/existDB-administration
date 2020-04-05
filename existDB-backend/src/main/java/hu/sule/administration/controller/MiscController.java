package hu.sule.administration.controller;


import hu.sule.administration.service.MiscService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/misc/")
public class MiscController {

    private MiscService miscServiceImpl;

    @Autowired
    public void setMiscServiceImpl(MiscService miscServiceImpl) {
        this.miscServiceImpl = miscServiceImpl;
    }

    @RequestMapping("/getVersion")
    public ResponseEntity<String> getVersion(){
        return new ResponseEntity<>("{\"response\":\"" + miscServiceImpl.getDbVersion() + "\"}", HttpStatus.OK);
    }

    @RequestMapping("/getServerIp")
    public ResponseEntity<String> getServerIp(){
        return new ResponseEntity<>("{\"response\":\"" + miscServiceImpl.getServerIp() + "\"}", HttpStatus.OK);
    }
}
