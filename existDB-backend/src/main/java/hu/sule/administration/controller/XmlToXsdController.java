package hu.sule.administration.controller;

import hu.sule.administration.model.ForStoreResourceAndColl;
import hu.sule.administration.xsdGenerator.XMLIsNotValidException;
import hu.sule.administration.xsdGenerator.XMLtoXSDService;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
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
    public String genXsd(HttpEntity<String> httpEntity) throws SAXException, IOException, XMLIsNotValidException, JDOMException {
        System.out.println(xmLtoXSDService.convert(httpEntity.getBody()));
        return xmLtoXSDService.convert(httpEntity.getBody());
    }

    @RequestMapping("/saveXsd")
    public String saveXsd(@RequestBody ForStoreResourceAndColl storeResource) throws XMLDBException{
        return xmLtoXSDService.saveXsd(storeResource);
    }

}
