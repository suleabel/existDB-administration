package hu.sule.administration.xsdGenerator;

import hu.sule.administration.exceptions.XMLIsNotValidException;
import hu.sule.administration.util.Mappers;
import org.jdom2.JDOMException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.junit.Assert.*;

public class XMLtoXSDServiceTest {

    @Autowired
    private XMLtoXSDService xmLtoXSDService;

    @Test
    public void convert() throws JDOMException, IOException, XMLIsNotValidException, SAXException {
//        String XmlInput = "<a><b/></a>";
//        System.out.println(xmLtoXSDService.convert(XmlInput));
//        assertEquals(xmLtoXSDService.convert(XmlInput), "");
    }

}