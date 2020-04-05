package hu.sule.administration.xsdGenerator;

import hu.sule.administration.exceptions.XMLIsNotValidException;
import hu.sule.administration.model.ForStoreResourceAndColl;
import hu.sule.administration.service.CollectionService;
import org.exolab.castor.xml.schema.Schema;
import org.exolab.castor.xml.schema.util.XMLInstance2Schema;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmldb.api.base.XMLDBException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;

@Service
public class XMLtoXSDService {

    private static final Logger logger = LoggerFactory.getLogger(XMLtoXSDService.class);

    @Autowired
    private CollectionService collectionServiceImpl;

    public String convert(String xml) throws JDOMException, IOException, XMLIsNotValidException, SAXException  {
        XMLInstance2Schema xmlInstance2Schema = new XMLInstance2Schema();
        Writer writer = new StringWriter();
        String generatedXSD = "";
        Document doc = null;
        if (!validateXML(xml)) {
            throw new XMLIsNotValidException("XML is not valid!: " + xml);
        }
        Schema schema = xmlInstance2Schema.createSchema(new InputSource(new StringReader(xml)));
        xmlInstance2Schema.serializeSchema(writer, schema);
        generatedXSD = writer.toString();
        writer.close();
        doc = new SAXBuilder().build(new StringReader(generatedXSD));
        return new XMLOutputter(Format.getPrettyFormat()).outputString(doc);
    }

    private boolean validateXML(String xml) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);
        try {
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            reader.setErrorHandler(new SimpleErrorHandler());
            reader.parse(new InputSource(new StringReader(xml)));
        } catch (SAXException saxe) {
            logger.error("SAXException during validation: " + saxe.getMessage());
            return false;
        } catch (ParserConfigurationException pce) {
            logger.error("ParserConfigurationException during validation: " + pce.getMessage());
            return false;
        } catch (IOException ioe) {
            logger.error("IOException during validation: " + ioe.getMessage());
            return false;
        }
        return true;
    }

    public String saveXsd(ForStoreResourceAndColl storeResource) throws XMLDBException {
        return collectionServiceImpl.Store(storeResource);
    }

}
