package com.example.demo.controller;

import com.example.demo.service.ExistDbMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/test/")
public class TestController {

    @Autowired
    private ExistDbMainService existDbMainService;

//    @RequestMapping("testTree")
//    public List<String> getTest(){
//        return existDbMainService.getCollectionFree("/db");
//    }
}
