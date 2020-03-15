package hu.sule.administration.service;

import hu.sule.administration.model.FileManagerEntity;
import hu.sule.administration.queries.ExistDbFileExplorerQueries;
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
public class FileExplorerService {
    @Autowired
    private ExistDbFileExplorerQueries existDbFileExplorerQueries;

    private static final Logger logger = LoggerFactory.getLogger(FileExplorerService.class);

    public List<FileManagerEntity> getDirectoryContent(String dirname) {
        List<FileManagerEntity> fileManagerEntities = new ArrayList<>();

        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = null;
        Namespace ns = Namespace.getNamespace("http://exist-db.org/collection-config/1.0");
        try {
            doc = saxBuilder.build(new InputSource(new StringReader(existDbFileExplorerQueries.getDirectoryContent(ExistDbCredentialsService.getDetails(), dirname))));
        } catch (JDOMException | IOException e){
            logger.error("SAXBuilder exception: " + e.getMessage()) ;
        }
        if(doc != null) {
            Element list = doc.getRootElement();
            List<Element> content = list.getChildren();
            for (Element file: content) {
                FileManagerEntity fme = null;
                if(file.getName().equals("file")){
                    fme = new FileManagerEntity(true,file.getAttributeValue("name"),file.getAttributeValue("size"),file.getAttributeValue("human-size"),file.getAttributeValue("modified"),file.getAttributeValue("hidden").equals("true"),file.getAttributeValue("canRead").equals("true"),file.getAttributeValue("canWrite").equals("true"));
                }else if(file.getName().equals("directory")){
                    fme = new FileManagerEntity(false,file.getAttributeValue("name"),file.getAttributeValue("size"),file.getAttributeValue("human-size"),file.getAttributeValue("modified"),file.getAttributeValue("hidden").equals("true"),file.getAttributeValue("canRead").equals("true"),file.getAttributeValue("canWrite").equals("true"));
                }
                fileManagerEntities.add(fme);
            }
        }
        return fileManagerEntities;
    }

    public String getRootDirectory() {
        return existDbFileExplorerQueries.getRootDirectory(ExistDbCredentialsService.getDetails());
    }

    public String getFileContent(String url) {
        return existDbFileExplorerQueries.readFileContent(ExistDbCredentialsService.getDetails(), url);
    }
}
