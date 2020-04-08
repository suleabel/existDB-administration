package hu.sule.administration.service;

import org.jdom2.JDOMException;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public interface QueryService {
    String evalXqueryAsString(String query) throws IOException, JDOMException;
    String evalXqueryAsPath(String query) throws IOException, JDOMException;
}
