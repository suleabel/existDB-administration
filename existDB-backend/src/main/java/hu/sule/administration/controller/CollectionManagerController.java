package hu.sule.administration.controller;

import hu.sule.administration.model.ExistCollectionManagerModel;
import hu.sule.administration.model.ForStoreResourceAndColl;
import hu.sule.administration.model.ResourceReadModel;
import hu.sule.administration.service.CollectionService;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/collection/")
public class CollectionManagerController {

    private CollectionService collectionServiceImpl;

    @Autowired
    public void setCollectionServiceImpl(CollectionService collectionServiceImpl) {
        this.collectionServiceImpl = collectionServiceImpl;
    }

    @RequestMapping("/getOnlyCollections")
    public ArrayList<ExistCollectionManagerModel> getOnlyCollectionsByCollection(HttpEntity<String> httpEntity) throws IOException{
        return collectionServiceImpl.getFileManagerCollectionsByCollection(httpEntity.getBody());
    }

    @RequestMapping("/getAllContentByCollection")
    public ArrayList<ExistCollectionManagerModel> getCollectionContent(HttpEntity<String> httpEntity) throws IOException{
        return collectionServiceImpl.getFileManagerContentByCollection(httpEntity.getBody());
    }

    @RequestMapping("/createDir")
    public ResponseEntity<String> createDir(@RequestBody ForStoreResourceAndColl storeCollection) {
        return new ResponseEntity<>("{\"response\":\"" + collectionServiceImpl.createDir(storeCollection) + "\"}", HttpStatus.OK);
    }

    @RequestMapping("/getFileContent")
    public ResponseEntity<ResourceReadModel> getFileContent(HttpEntity<String> httpEntity) throws JDOMException, IOException{
        return new ResponseEntity<>(collectionServiceImpl.readFile(httpEntity.getBody()),HttpStatus.OK);
    }

    @RequestMapping("/store")
    public ResponseEntity<String> store(@RequestBody ForStoreResourceAndColl storeResource) {
        return new ResponseEntity<>("{\"response\":\"" + collectionServiceImpl.Store(storeResource) + "\"}", HttpStatus.OK);
    }

    @RequestMapping("/saveEdit")
    public ResponseEntity<String> saveEdit(@RequestBody ForStoreResourceAndColl storeResource) {
        return new ResponseEntity<>("{\"response\":\"" + collectionServiceImpl.saveEditedRes(storeResource) + "\"}", HttpStatus.OK);
    }

    @RequestMapping("/deleteRes")
    public ResponseEntity<String> deleteRes(@RequestBody ExistCollectionManagerModel existCollectionManagerModel) {
        return new ResponseEntity<>("{\"response\":\"" + collectionServiceImpl.deleteFile(existCollectionManagerModel) + "\"}", HttpStatus.OK);
    }

    @RequestMapping("/deleteColl")
    public ResponseEntity<String> deleteCollection(@RequestBody ExistCollectionManagerModel existCollectionManagerModel) {
        return new ResponseEntity<>("{\"response\":\"" + collectionServiceImpl.deleteCollection(existCollectionManagerModel) + "\"}", HttpStatus.OK);
    }

    @RequestMapping("/editResCred")
    public ResponseEntity<String> editResCred(@RequestBody ExistCollectionManagerModel existCollectionManagerModel) {
        return new ResponseEntity<>("{\"response\":\"" + collectionServiceImpl.editResCred(existCollectionManagerModel) + "\"}", HttpStatus.OK);
    }

    @RequestMapping("/unlockResource")
    public ResponseEntity<String> unlockResource(HttpEntity<String> httpEntity) {
        return new ResponseEntity<>("{\"response\":\"" + collectionServiceImpl.unlockResource(httpEntity.getBody()) + "\"}", HttpStatus.OK);
    }
}
