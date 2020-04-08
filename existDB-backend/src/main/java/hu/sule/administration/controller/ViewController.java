package hu.sule.administration.controller;

import hu.sule.administration.model.CreatedViewModel;
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

import java.io.IOException;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/view/")
public class ViewController {

    private ViewService viewServiceImpl;

    @Autowired
    public void setViewServiceImpl(ViewService viewServiceImpl) {
        this.viewServiceImpl = viewServiceImpl;
    }

    @RequestMapping("/createView")
    public ResponseEntity<String> createView(@RequestBody ViewCreateModel viewCreateModel) {
        viewServiceImpl.createViewTrigger(viewCreateModel);
        return new ResponseEntity<>("{\"response\":\"success\"}",HttpStatus.OK);
    }

    @RequestMapping("/getCreatedViews")
    public ResponseEntity<ArrayList<CreatedViewModel>> getCreatedViews() throws IOException, JDOMException {
        return new ResponseEntity<>(viewServiceImpl.getCreatedViews(), HttpStatus.OK);
    }
}
