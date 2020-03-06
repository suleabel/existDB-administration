package hu.sule.administration.controller;


import hu.sule.administration.model.ForStoreResourceAndColl;
import hu.sule.administration.xsdGenerator.XMLtoXSDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/xml2xsd/")
public class XmlToXsdController {

    @Autowired
    private XMLtoXSDService xmLtoXSDService;

    @RequestMapping("/createXsd")
    public String genXsd(HttpEntity<String> httpEntity){
        System.out.println(xmLtoXSDService.convert(httpEntity.getBody()));
        return xmLtoXSDService.convert(httpEntity.getBody());
    }

    @RequestMapping("/saveXsd")
    public String saveXsd(@RequestBody ForStoreResourceAndColl storeResource){
        return xmLtoXSDService.saveXsd(storeResource);
    }

}
