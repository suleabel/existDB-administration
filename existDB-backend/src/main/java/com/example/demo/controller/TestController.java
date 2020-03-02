package com.example.demo.controller;

import com.example.demo.service.ExistDbCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/test/")
public class TestController {

    @Autowired
    private ExistDbCredentialsService existDbCredentialsService;

//    @RequestMapping("testTree")
//    public List<String> getTest(){
//        return existDbMainService.getCollectionFree("/db");
//    }
}
