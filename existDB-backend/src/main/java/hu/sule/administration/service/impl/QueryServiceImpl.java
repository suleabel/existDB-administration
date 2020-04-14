package hu.sule.administration.service.impl;

import hu.sule.administration.queries.ExistDbExecuteQueries;
import hu.sule.administration.service.QueryService;
import hu.sule.administration.util.XmlValidator;
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

    public String evalXqueryAsString(String query) throws IOException, JDOMException  {
        String result = existDbExecuteQueries.evalXqueryasString(ExistDbCredentialsServiceImpl.getDetails(),query.replaceAll("\"","'"));
        if(XmlValidator.validateXML(result)){
            return  new XMLOutputter(Format.getPrettyFormat()).outputString(new SAXBuilder().build(
                    new StringReader(existDbExecuteQueries.evalXqueryasString(ExistDbCredentialsServiceImpl.getDetails(),query.replaceAll("\"","'")).replaceAll("\"","'"))));
        }
        return result;

    }

    public String evalXqueryAsPath(String query) throws IOException, JDOMException {
        return new XMLOutputter(Format.getPrettyFormat()).outputString(new SAXBuilder().build(new StringReader(existDbExecuteQueries.evalXqueryasPath(ExistDbCredentialsServiceImpl.getDetails(),query))));
        }
}
