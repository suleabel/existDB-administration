package hu.sule.administration.controller;

import hu.sule.administration.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/test/")
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("test")
    public ResponseEntity<String> getTest() throws Exception {
        testService.testCucc();
        return new ResponseEntity<>("Error" , HttpStatus.INTERNAL_SERVER_ERROR);
        //throw new ApiRequestException("working");
    }
}
