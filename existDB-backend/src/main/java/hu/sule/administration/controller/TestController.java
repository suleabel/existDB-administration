package hu.sule.administration.controller;

import hu.sule.administration.service.ExistDbCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/test/")
public class TestController {

    @Autowired
    private ExistDbCredentialsService existDbCredentialsService;

//    @RequestMapping("testTree")
//    public List<String> getTest(){
//        return existDbMainService.getCollectionFree("/db");
//    }
}
