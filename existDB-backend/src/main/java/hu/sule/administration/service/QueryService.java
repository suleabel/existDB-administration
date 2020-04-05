package hu.sule.administration.service;

import org.jdom2.JDOMException;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public interface QueryService {
    String evalXqueryasString(String query) throws IOException, JDOMException;
    String evalXqueryasPath(String query) throws IOException, JDOMException;
}
