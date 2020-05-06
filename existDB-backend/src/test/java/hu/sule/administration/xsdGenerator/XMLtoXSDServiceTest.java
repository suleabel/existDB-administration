package hu.sule.administration.xsdGenerator;

import hu.sule.administration.exceptions.XMLIsNotValidException;
import hu.sule.administration.util.Mappers;
import org.jdom2.JDOMException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XMLtoXSDServiceTest {

    @Autowired
    private XMLtoXSDService xmLtoXSDService;

    @Test
    public void convert() throws JDOMException, IOException, XMLIsNotValidException, SAXException {
//        String XmlInput = "<a><b/></a>";
//        String expectedResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                "<schema xmlns=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
//                "  <element name=\"a\">\n" +
//                "    <complexType>\n" +
//                "      <sequence>\n" +
//                "        <element name=\"b\" />\n" +
//                "      </sequence>\n" +
//                "    </complexType>\n" +
//                "  </element>\n" +
//                "</schema>\n";
//        assertEquals(expectedResult, xmLtoXSDService.convert(XmlInput));
    }

}