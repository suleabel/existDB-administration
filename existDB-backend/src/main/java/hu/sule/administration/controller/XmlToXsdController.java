package hu.sule.administration.controller;

import hu.sule.administration.model.ForStoreResourceAndColl;
import hu.sule.administration.exceptions.XMLIsNotValidException;
import hu.sule.administration.model.ResourceReadModel;
import hu.sule.administration.xsdGenerator.XMLtoXSDService;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/xml2xsd/")
public class XmlToXsdController {

    @Autowired
    private XMLtoXSDService xmLtoXSDService;

    @RequestMapping("/createXsd")
    public ResponseEntity<ResourceReadModel> genXsd(HttpEntity<String> httpEntity) throws SAXException, IOException, XMLIsNotValidException, JDOMException {
        return new ResponseEntity<>(new ResourceReadModel(xmLtoXSDService.convert(httpEntity.getBody()), false), HttpStatus.OK);
    }

    @RequestMapping("/saveXsd")
    public ResponseEntity<String> saveXsd(@RequestBody ForStoreResourceAndColl storeResource){
        return new ResponseEntity<>("{\"response\":\"" + xmLtoXSDService.saveXsd(storeResource) + "\"}",HttpStatus.OK);
    }

}
