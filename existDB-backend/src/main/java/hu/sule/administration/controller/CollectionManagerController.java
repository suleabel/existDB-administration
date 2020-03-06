package hu.sule.administration.controller;

import hu.sule.administration.model.ExistFileManagerModel;
import hu.sule.administration.model.ForStoreResourceAndColl;
import hu.sule.administration.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/collection/")
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

    @RequestMapping("/getFileContent")
    public String getFileContent(HttpEntity<String> httpEntity){
        return collectionService.readFile(httpEntity.getBody());
    }

    @RequestMapping("/storeBin")
    public String storeBin(@RequestBody ForStoreResourceAndColl storeResource){
        return collectionService.storeResourceBin(storeResource);
    }

    @RequestMapping("/storeXml")
    public String storeXml(@RequestBody ForStoreResourceAndColl storeResource) {
        return collectionService.storeResourceXml(storeResource);
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
