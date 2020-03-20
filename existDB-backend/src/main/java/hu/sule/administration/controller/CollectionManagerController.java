package hu.sule.administration.controller;

import hu.sule.administration.model.ExistCollectionManagerModel;
import hu.sule.administration.model.ForStoreResourceAndColl;
import hu.sule.administration.model.ResourceReadModel;
import hu.sule.administration.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/collection/")
public class CollectionManagerController {

    @Autowired
    private CollectionService collectionService;

    @RequestMapping("/getOnlyCollections")
    public ArrayList<ExistCollectionManagerModel> getOnlyCollectionsByCollection(HttpEntity<String> httpEntity){
        return collectionService.getFileManagerCollectionsByCollection(httpEntity.getBody());
    }

    @RequestMapping("/getAllContentByCollection")
    public ArrayList<ExistCollectionManagerModel> getCollectionContent(HttpEntity<String> httpEntity){
        return collectionService.getFileManagerContentByCollection(httpEntity.getBody());
    }

    @RequestMapping("/createDir")
    public String createDir(@RequestBody ForStoreResourceAndColl storeCollection) {
        return collectionService.createDir(storeCollection);
    }

    @RequestMapping("/getFileContent")
    public ResourceReadModel getFileContent(HttpEntity<String> httpEntity){
        return collectionService.readFile(httpEntity.getBody());
    }

    @RequestMapping("/store")
    public String store(@RequestBody ForStoreResourceAndColl storeResource){

        return collectionService.Store(storeResource);
    }

    @RequestMapping("/saveEdit")
    public String saveEdit(@RequestBody ForStoreResourceAndColl storeResource){
        return collectionService.saveEditedRes(storeResource);
    }

    @RequestMapping("/deleteRes")
    public String deleteRes(@RequestBody ExistCollectionManagerModel existCollectionManagerModel){
        return collectionService.deleteFile(existCollectionManagerModel);
    }

    @RequestMapping("/deleteColl")
    public String deleteCollection(@RequestBody ExistCollectionManagerModel existCollectionManagerModel){
        return collectionService.deleteCollection(existCollectionManagerModel);
    }

    @RequestMapping("/editResCred")
    public String editResCred(@RequestBody ExistCollectionManagerModel existCollectionManagerModel){
        return collectionService.editResCred(existCollectionManagerModel);
    }

    @RequestMapping("/evalXqueryasString")
    public String evalXqueryasString(HttpEntity<String> httpEntity){
        return collectionService.evalXqueryasString(httpEntity.getBody());
    }

    @RequestMapping("/evalXqueryasPath")
    public String evalXqueryasPath(HttpEntity<String> httpEntity){
        return collectionService.evalXqueryasString(httpEntity.getBody());
    }
}
