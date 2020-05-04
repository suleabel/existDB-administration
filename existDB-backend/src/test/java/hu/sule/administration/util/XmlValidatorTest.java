package hu.sule.administration.util;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class XmlValidatorTest {

    @Test
    public void validateXML() {
        String testXml = "<a><b/></a>";
        assertTrue(XmlValidator.validateXML(testXml));
    }

    @Test
    public void validateXMLNoInput() {
        String testXml = "";
        assertFalse(XmlValidator.validateXML(testXml));
    }

    @Test
    public void validateXMLNoXmlInput() {
        String testXml = "noXmlInput";
        assertFalse(XmlValidator.validateXML(testXml));
    }

    @Test
    public void validateXMLInvalidXmlInput() {
        String testXml = "<a><b></a>";
        assertFalse(XmlValidator.validateXML(testXml));
    }
}