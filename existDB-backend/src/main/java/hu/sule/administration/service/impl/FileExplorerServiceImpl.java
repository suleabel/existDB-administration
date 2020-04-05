package hu.sule.administration.service.impl;

import hu.sule.administration.model.FileManagerEntity;
import hu.sule.administration.model.SerializeFile;
import hu.sule.administration.model.StoreDirOrFileModel;
import hu.sule.administration.queries.ExistDbFileExplorerQueries;
import hu.sule.administration.service.FileExplorerService;
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

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileExplorerServiceImpl implements FileExplorerService {
    @Autowired
    private ExistDbFileExplorerQueries existDbFileExplorerQueries;

    private static final Logger logger = LoggerFactory.getLogger(FileExplorerServiceImpl.class);

    public List<FileManagerEntity> getDirectoryContent(String dirname) {
        List<FileManagerEntity> fileManagerEntities = new ArrayList<>();
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = null;
        Namespace ns = Namespace.getNamespace("http://exist-db.org/collection-config/1.0");
        try {
            doc = saxBuilder.build(new InputSource(new StringReader(existDbFileExplorerQueries.getDirectoryContent(ExistDbCredentialsServiceImpl.getDetails(), dirname))));
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

    public String getRootDirectory() {
        return existDbFileExplorerQueries.getRootDirectory(ExistDbCredentialsServiceImpl.getDetails());
    }

    public String getFileContent(String url) throws JDOMException, IOException {
        // XML 1.0
        // #x9 | #xA | #xD | [#x20-#xD7FF] | [#xE000-#xFFFD] | [#x10000-#x10FFFF]
        String xml10pattern = "[^"
                + "\u0009\r\n"
                + "\u0020-\uD7FF"
                + "\uE000-\uFFFD"
                + "\ud800\udc00-\udbff\udfff"
                + "]";
        String xmlString = existDbFileExplorerQueries.readFileContent(ExistDbCredentialsServiceImpl.getDetails(), url).replaceAll(xml10pattern, "");
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
        return existDbFileExplorerQueries.mkDir(ExistDbCredentialsServiceImpl.getDetails(),storeDirOrFileModel);
    }

    public String delete(StoreDirOrFileModel storeDirOrFileModel) {
        return existDbFileExplorerQueries.delete(ExistDbCredentialsServiceImpl.getDetails(),storeDirOrFileModel);
    }

    public String createFile(SerializeFile file){
        if("true".equals(file.getIsXml())){
            return existDbFileExplorerQueries.createXmlFile(ExistDbCredentialsServiceImpl.getDetails(), file);
        }
        return existDbFileExplorerQueries.createFile(ExistDbCredentialsServiceImpl.getDetails(), file);
    }

    public String editFile(SerializeFile file){
        String result;
        if("true".equals(file.getIsXml())){
            result = existDbFileExplorerQueries.editXmlFile(ExistDbCredentialsServiceImpl.getDetails(), file);

        } else {
            result = existDbFileExplorerQueries.editFile(ExistDbCredentialsServiceImpl.getDetails(), file);
        }
        if(result.contains("true")){
            return "Success";
        }
        return "failure";
    }
}
