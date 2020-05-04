package hu.sule.administration.xsdGenerator;

import hu.sule.administration.exceptions.XMLIsNotValidException;
import hu.sule.administration.model.ForStoreResourceAndColl;
import hu.sule.administration.service.CollectionService;
import hu.sule.administration.util.XmlValidator;
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
import org.xmldb.api.base.XMLDBException;

import java.io.*;

@Service
public class XMLtoXSDService {

    @Autowired
    private CollectionService collectionServiceImpl;

    public String convert(String xml) throws JDOMException, IOException, XMLIsNotValidException, SAXException {
        XMLInstance2Schema xmlInstance2Schema = new XMLInstance2Schema();
        Writer writer = new StringWriter();
        String generatedXSD = "";
        Document doc = null;
        if (!XmlValidator.validateXML(xml)) {
            throw new XMLIsNotValidException("XML is not valid!: " + xml);
        }
        Schema schema = xmlInstance2Schema.createSchema(new InputSource(new StringReader(xml)));
        xmlInstance2Schema.serializeSchema(writer, schema);
        generatedXSD = writer.toString();
        writer.close();
        doc = new SAXBuilder().build(new StringReader(generatedXSD));
        return new XMLOutputter(Format.getPrettyFormat()).outputString(doc);
    }

    public String saveXsd(ForStoreResourceAndColl storeResource) {
        return collectionServiceImpl.Store(storeResource);
    }

}
