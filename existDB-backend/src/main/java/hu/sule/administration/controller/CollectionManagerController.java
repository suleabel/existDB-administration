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
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/collection/")
public class CollectionManagerController {

    @Autowired
    private CollectionService collectionService;

    @RequestMapping("/getOnlyCollections")
    public ArrayList<ExistCollectionManagerModel> getOnlyCollectionsByCollection(HttpEntity<String> httpEntity) throws XMLDBException, JDOMException, IOException{
        return collectionService.getFileManagerCollectionsByCollection(httpEntity.getBody());
    }

    @RequestMapping("/getAllContentByCollection")
    public ArrayList<ExistCollectionManagerModel> getCollectionContent(HttpEntity<String> httpEntity) throws XMLDBException, JDOMException, IOException{
        return collectionService.getFileManagerContentByCollection(httpEntity.getBody());
    }

    @RequestMapping("/createDir")
    public String createDir(@RequestBody ForStoreResourceAndColl storeCollection) throws XMLDBException{
        return collectionService.createDir(storeCollection);
    }

    @RequestMapping("/getFileContent")
    public ResourceReadModel getFileContent(HttpEntity<String> httpEntity) throws XMLDBException{
        return collectionService.readFile(httpEntity.getBody());
    }

    @RequestMapping("/store")
    public String store(@RequestBody ForStoreResourceAndColl storeResource) throws XMLDBException{
        return collectionService.Store(storeResource);
    }

    @RequestMapping("/saveEdit")
    public String saveEdit(@RequestBody ForStoreResourceAndColl storeResource) throws XMLDBException{
        return collectionService.saveEditedRes(storeResource);
    }

    @RequestMapping("/deleteRes")
    public String deleteRes(@RequestBody ExistCollectionManagerModel existCollectionManagerModel) throws XMLDBException{
        return collectionService.deleteFile(existCollectionManagerModel);
    }

    @RequestMapping("/deleteColl")
    public String deleteCollection(@RequestBody ExistCollectionManagerModel existCollectionManagerModel) throws XMLDBException{
        return collectionService.deleteCollection(existCollectionManagerModel);
    }

    @RequestMapping("/editResCred")
    public String editResCred(@RequestBody ExistCollectionManagerModel existCollectionManagerModel) throws XMLDBException{
        return collectionService.editResCred(existCollectionManagerModel);
    }

    @RequestMapping("/evalXqueryasString")
    public ResponseEntity<String> evalXqueryasString(HttpEntity<String> httpEntity) throws XMLDBException{
        String result = collectionService.evalXqueryasString(httpEntity.getBody());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping("/evalXqueryasPath")
    public ResponseEntity<String> evalXqueryasPath(HttpEntity<String> httpEntity) throws XMLDBException, JDOMException, IOException {
        String result = collectionService.evalXqueryasPath(httpEntity.getBody());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping("/unlockResource")
    public String unlockResource(HttpEntity<String> httpEntity) throws XMLDBException{
        return collectionService.unlockResource(httpEntity.getBody());
    }
}
