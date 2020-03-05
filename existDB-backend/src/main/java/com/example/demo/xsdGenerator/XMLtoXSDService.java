package com.example.demo.xsdGenerator;
import com.example.demo.model.ForStoreResourceAndColl;
import com.example.demo.service.CollectionService;
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

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;

@Service
public class XMLtoXSDService {

    private static final Logger logger = LoggerFactory.getLogger(XMLtoXSDService.class);

    @Autowired
    private CollectionService collectionService;

    public String convert(String xml) {

        XMLInstance2Schema xmlInstance2Schema = new XMLInstance2Schema();
        Writer writer = new StringWriter();
        String generatedXSD = "";
        Document doc = null;
        try {
            if (!validateXML(xml)) {
                throw new XMLIsNotValidException("XML is not valid!: " + xml);
            }
            Schema schema = xmlInstance2Schema.createSchema(new InputSource(new StringReader(xml)));
            xmlInstance2Schema.serializeSchema(writer, schema);
            generatedXSD = writer.toString();
            writer.close();
            doc = new SAXBuilder().build(new StringReader(generatedXSD));
        } catch (XMLIsNotValidException e){
            logger.error("XMLIsNotValidException: " + e.getMessage());
        } catch(IOException ioe){
            logger.error("IOException: " + ioe.getMessage());
        } catch(SAXException saxe) {
            logger.error("SAXException: " + saxe.getMessage());
        } catch (JDOMException jdome) {
            logger.error("JDOMException: " + jdome.getMessage());
        }
    return new XMLOutputter(Format.getPrettyFormat()).outputString(doc);
    }

    private boolean validateXML(String xml){
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);
        try{
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            reader.setErrorHandler(new SimpleErrorHandler());
            reader.parse(new InputSource( new StringReader( xml )));
        }catch (SAXException saxe){
            logger.error("SAXException during validation: " + saxe.getMessage());
            return false;
        }catch (ParserConfigurationException pce){
            logger.error("ParserConfigurationException during validation: " + pce.getMessage());
            return false;
        }catch (IOException ioe){
            logger.error("IOException during validation: " + ioe.getMessage());
            return false;
        }
        return true;
    }

    public String saveXsd(ForStoreResourceAndColl storeResource) {
        return collectionService.storeResourceBin(storeResource);
    }

}
