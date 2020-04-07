package hu.sule.administration.service.impl;

import hu.sule.administration.queries.ExistDbExecuteQueries;
import hu.sule.administration.service.QueryService;
import hu.sule.administration.xsdGenerator.SimpleErrorHandler;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.StringReader;

@Service
public class QueryServiceImpl implements QueryService {
    @Autowired
    private ExistDbExecuteQueries existDbExecuteQueries;

    public String evalXqueryasString(String query) throws IOException, JDOMException  {
        String result = existDbExecuteQueries.evalXqueryasString(ExistDbCredentialsServiceImpl.getDetails(),query.replaceAll("\"","'"));
        if(isXml(result)){
            return  new XMLOutputter(Format.getPrettyFormat()).outputString(new SAXBuilder().build(
                    new StringReader(existDbExecuteQueries.evalXqueryasString(ExistDbCredentialsServiceImpl.getDetails(),query.replaceAll("\"","'")).replaceAll("\"","'"))));
        }
        return result;

    }

    public String evalXqueryasPath(String query) throws IOException, JDOMException {
        return new XMLOutputter(Format.getPrettyFormat()).outputString(new SAXBuilder().build(new StringReader(existDbExecuteQueries.evalXqueryasPath(ExistDbCredentialsServiceImpl.getDetails(),query))));
    }

    public boolean isXml(String input){
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);
        try {
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            reader.setErrorHandler(new SimpleErrorHandler());
            reader.parse(new InputSource(new StringReader(input)));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
