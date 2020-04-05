package hu.sule.administration.controller;

import hu.sule.administration.model.ViewCreateModel;
import hu.sule.administration.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
