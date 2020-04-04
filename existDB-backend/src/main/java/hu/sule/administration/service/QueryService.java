package hu.sule.administration.service;

import hu.sule.administration.queries.ExistDbExecuteQueries;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;

@Service
public class QueryService {
    @Autowired
    private ExistDbExecuteQueries existDbExecuteQueries;

    public String evalXqueryasString(String query) {
        return existDbExecuteQueries.evalXqueryasString(ExistDbCredentialsService.getDetails(),query.replaceAll("\"","'")).replaceAll("\"","'");
    }

    public String evalXqueryasPath(String query) throws IOException, JDOMException {
        return new XMLOutputter(Format.getPrettyFormat()).outputString(new SAXBuilder().build(new StringReader(existDbExecuteQueries.evalXqueryasPath(ExistDbCredentialsService.getDetails(),query))));
    }
}
