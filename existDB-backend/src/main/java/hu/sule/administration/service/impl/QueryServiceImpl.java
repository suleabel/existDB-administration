package hu.sule.administration.service.impl;

import hu.sule.administration.queries.ExistDbExecuteQueries;
import hu.sule.administration.service.QueryService;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;

@Service
public class QueryServiceImpl implements QueryService {
    @Autowired
    private ExistDbExecuteQueries existDbExecuteQueries;

    public String evalXqueryasString(String query) throws IOException, JDOMException  {
        return  new XMLOutputter(Format.getPrettyFormat()).outputString(new SAXBuilder().build(
                new StringReader(existDbExecuteQueries.evalXqueryasString(ExistDbCredentialsServiceImpl.getDetails(),query.replaceAll("\"","'")).replaceAll("\"","'"))));
    }

    public String evalXqueryasPath(String query) throws IOException, JDOMException {
        return new XMLOutputter(Format.getPrettyFormat()).outputString(new SAXBuilder().build(new StringReader(existDbExecuteQueries.evalXqueryasPath(ExistDbCredentialsServiceImpl.getDetails(),query))));
    }
}
