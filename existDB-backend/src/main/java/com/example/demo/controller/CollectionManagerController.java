package com.example.demo.controller;

import com.example.demo.model.ExistFileManagerModel;
import com.example.demo.model.ForStoreResourceAndColl;
import com.example.demo.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/existCollection/")
public class CollectionManagerController {

    @Autowired
    private CollectionService collectionService;


    @RequestMapping("/getOnlyCollections")
    public ArrayList<ExistFileManagerModel> getOnlyCollectionsByCollection(HttpEntity<String> httpEntity){
        return collectionService.getCollectionContainedCollections(httpEntity.getBody());
    }

    @RequestMapping("/getAllContentByCollection")
    public ArrayList<ExistFileManagerModel> getCollectionContent(HttpEntity<String> httpEntity){
        return collectionService.getFileManagerContentByCollection(httpEntity.getBody());
    }

    @RequestMapping("/createDir")
    public String createDir(@RequestBody ForStoreResourceAndColl storeCollection) {
        System.out.println("createDir" + storeCollection.toString());
        return collectionService.createDir(storeCollection);
    }

    @RequestMapping("/getBinResContent")
    public String getBinResContent(HttpEntity<String> httpEntity){
        return collectionService.readBinaryFile(httpEntity.getBody());
    }

    @RequestMapping("/store")
    public String saveXsd(@RequestBody ForStoreResourceAndColl storeResource){
        return collectionService.storeResource(storeResource);
    }

    @RequestMapping("/deleteRes")
    public String deleteRes(@RequestBody ExistFileManagerModel existFileManagerModel){
        return collectionService.deleteFile(existFileManagerModel);
    }

    @RequestMapping("/deleteColl")
    public String deleteCollection(@RequestBody ExistFileManagerModel existFileManagerModel){
        return collectionService.deleteCollection(existFileManagerModel);
    }


    @RequestMapping("/editResCred")
    public String editResCred(@RequestBody ExistFileManagerModel existFileManagerModel){
        System.out.println(existFileManagerModel.toString());
        return collectionService.editResCred(existFileManagerModel);
    }
}
