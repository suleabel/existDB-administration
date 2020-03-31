package hu.sule.administration.service;

import hu.sule.administration.exceptions.CustomeException;
import hu.sule.administration.model.FileManagerEntity;
import hu.sule.administration.model.SerializeFile;
import hu.sule.administration.model.StoreDirOrFileModel;
import hu.sule.administration.queries.ExistDbFileExplorerQueries;
import hu.sule.administration.xsdGenerator.SimpleErrorHandler;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileExplorerService {
    @Autowired
    private ExistDbFileExplorerQueries existDbFileExplorerQueries;

    private static final Logger logger = LoggerFactory.getLogger(FileExplorerService.class);

    public List<FileManagerEntity> getDirectoryContent(String dirname) throws XMLDBException {
        List<FileManagerEntity> fileManagerEntities = new ArrayList<>();
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = null;
        Namespace ns = Namespace.getNamespace("http://exist-db.org/collection-config/1.0");
        try {
            doc = saxBuilder.build(new InputSource(new StringReader(existDbFileExplorerQueries.getDirectoryContent(ExistDbCredentialsService.getDetails(), dirname))));
        } catch (JDOMException | IOException e) {
            logger.error("SAXBuilder exception: " + e.getMessage());
        }
        if (doc != null) {
            Element list = doc.getRootElement();
            List<Element> content = list.getChildren();
            for (Element file : content) {
                FileManagerEntity fme = null;
                if (file.getName().equals("file")) {
                    fme = new FileManagerEntity(true, file.getAttributeValue("name"), file.getAttributeValue("size"), file.getAttributeValue("human-size"), file.getAttributeValue("modified"), file.getAttributeValue("hidden").equals("true"), file.getAttributeValue("canRead").equals("true"), file.getAttributeValue("canWrite").equals("true"));
                } else if (file.getName().equals("directory")) {
                    fme = new FileManagerEntity(false, file.getAttributeValue("name"), file.getAttributeValue("size"), file.getAttributeValue("human-size"), file.getAttributeValue("modified"), file.getAttributeValue("hidden").equals("true"), file.getAttributeValue("canRead").equals("true"), file.getAttributeValue("canWrite").equals("true"));
                }
                fileManagerEntities.add(fme);
            }
        }
        return fileManagerEntities;
    }

    public String getRootDirectory() throws XMLDBException {
        return existDbFileExplorerQueries.getRootDirectory(ExistDbCredentialsService.getDetails());
    }

    public String getFileContent(String url) throws XMLDBException, JDOMException, IOException {
        // XML 1.0
        // #x9 | #xA | #xD | [#x20-#xD7FF] | [#xE000-#xFFFD] | [#x10000-#x10FFFF]
        String xml10pattern = "[^"
                + "\u0009\r\n"
                + "\u0020-\uD7FF"
                + "\uE000-\uFFFD"
                + "\ud800\udc00-\udbff\udfff"
                + "]";
        String xmlString = existDbFileExplorerQueries.readFileContent(ExistDbCredentialsService.getDetails(), url).replaceAll(xml10pattern, "");
        String content = "";
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = null;
        Namespace ns = Namespace.getNamespace("http://exist-db.org/collection-config/1.0");
        doc = saxBuilder.build(new InputSource(new StringReader(xmlString)));
        if (doc != null) {
            content = doc.getRootElement().getValue();
        }

        return content;
    }

    public String makeDir(StoreDirOrFileModel storeDirOrFileModel) {
        return existDbFileExplorerQueries.mkDir(ExistDbCredentialsService.getDetails(),storeDirOrFileModel);
    }

    public String delete(StoreDirOrFileModel storeDirOrFileModel) {
        return existDbFileExplorerQueries.delete(ExistDbCredentialsService.getDetails(),storeDirOrFileModel);
    }

    public String createFile(SerializeFile file){
        System.out.println(file.toString());
        if("true".equals(file.getIsXml())){
            return existDbFileExplorerQueries.createXmlFile(ExistDbCredentialsService.getDetails(), file);
        }
        return existDbFileExplorerQueries.createFile(ExistDbCredentialsService.getDetails(), file);
    }

    public String editFile(SerializeFile file){
        return existDbFileExplorerQueries.editFile(ExistDbCredentialsService.getDetails(), file);
    }
}
