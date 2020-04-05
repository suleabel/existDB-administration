package hu.sule.administration.controller;

import hu.sule.administration.service.QueryService;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/query/")

public class ExecuteQueryController {
    private QueryService queryServiceImpl;

    @Autowired
    public void setQueryServiceImpl(QueryService queryServiceImpl) {
        this.queryServiceImpl = queryServiceImpl;
    }

    @RequestMapping("/evalXqueryasString")
    public ResponseEntity<String> evalXqueryasString(HttpEntity<String> httpEntity) throws JDOMException, IOException  {
        return new ResponseEntity<>(queryServiceImpl.evalXqueryasString(httpEntity.getBody()), HttpStatus.OK);
    }

    @RequestMapping("/evalXqueryasPath")
    public ResponseEntity<String> evalXqueryasPath(HttpEntity<String> httpEntity) throws JDOMException, IOException {
        return new ResponseEntity<>(queryServiceImpl.evalXqueryasPath(httpEntity.getBody()), HttpStatus.OK);
    }
}
