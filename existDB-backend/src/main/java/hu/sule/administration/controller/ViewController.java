package hu.sule.administration.controller;

import hu.sule.administration.model.ViewCreateModel;
import hu.sule.administration.service.ViewService;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/view/")
public class ViewController {

    @Autowired
    private ViewService viewService;

    @RequestMapping("/createView")
    public ResponseEntity<String> createVire(@RequestBody ViewCreateModel viewCreateModel) throws IOException, JDOMException, XMLDBException {
        viewService.createViewTrigger(viewCreateModel);
        return new ResponseEntity<>("{\"response\":\"test\"}",HttpStatus.OK);
    }
}
