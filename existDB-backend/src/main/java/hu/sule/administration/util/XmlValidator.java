package hu.sule.administration.util;

import hu.sule.administration.xsdGenerator.SimpleErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.StringReader;


@Component
public class XmlValidator {

    private static final Logger logger = LoggerFactory.getLogger(XmlValidator.class);

    public static boolean validateXML(String xml) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);
        try {
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            reader.setErrorHandler(new SimpleErrorHandler());
            reader.parse(new InputSource(new StringReader(xml)));
        } catch (SAXException | ParserConfigurationException | IOException e) {
            logger.error("Exception during validation: " + e.getMessage());
            return false;
        }
        return true;
    }
}
