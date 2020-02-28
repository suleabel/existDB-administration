package com.example.demo.controller;

import com.example.demo.model.ExistFileManagerModel;
import com.example.demo.service.ExistDbMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/existCollection/")
public class ExistCollectionManagerController {

    @Autowired
    private ExistDbMainService existDbMainService;


    @RequestMapping("/getCollectionContent")
    public List<String> getRootCollection(HttpEntity<String> httpEntity){
        return existDbMainService.getCollection(httpEntity.getBody());
    }

    @RequestMapping("/getAllContentByCollection")
    public ArrayList<ExistFileManagerModel> getCollection(HttpEntity<String> httpEntity){
        return existDbMainService.getFileManagerContentByCollection(httpEntity.getBody());
    }
}
