package hu.sule.administration.service;

import hu.sule.administration.model.EditTriggerModel;
import hu.sule.administration.model.TriggerModel;
import hu.sule.administration.queries.ExistDBTriggerQueries;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
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
public class VersionManagerService {

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private ExistDBTriggerQueries existDBTriggerQueries;

    @Autowired
    private TriggerService triggerService;


    private static final Logger logger = LoggerFactory.getLogger(VersionManagerService.class);

    public boolean isEnabled() {
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = null;
        try {
            doc = saxBuilder.build(new InputSource(new StringReader(collectionService.readFile("/db/system/config/db/collection.xconf").getContent())));
        } catch (JDOMException | IOException e){
            logger.error("SAXBuilder exception: " + e.getMessage()) ;
        }
        if(doc != null) {
            Element collection = doc.getRootElement();
            List<Element> triggerList = collection.getChildren().get(0).getChildren();
            for (Element trigger : triggerList) {
                if(trigger.getAttributeValue("class").equals("org.exist.versioning.VersioningTrigger")){
                    return true;
                }
            }
        }else{
            return false;
        }
        return false;
    }

    public String enableVersioning(){
        List<String> events = new ArrayList<>();
        events.add("create");
        events.add("delete");
        events.add("update");
        events.add("copy");
        events.add("move");
        return triggerService.addTriggerToConfiguration(new TriggerModel(events,"org.exist.versioning.VersioningTrigger","overwrite","yes"), "/db/system/config/db");
    }

//    public boolean enableVersionManager(){
//        SAXBuilder saxBuilder = new SAXBuilder();
//        Document doc = null;
//        Namespace ns = Namespace.getNamespace("http://exist-db.org/collection-config/1.0");
//        try {
//            doc = saxBuilder.build(new InputSource(new StringReader(collectionService.readFile("/db/system/config/db/collection.xconf").getContent())));
//        } catch (JDOMException | IOException e){
//            logger.error("SAXBuilder exception: " + e.getMessage()) ;
//        }
//        if(doc != null) {
//
//            Element collection = doc.getRootElement();
//            List<Element> triggers = collection.getChildren().get(0).getChildren();
//            Element parameter = new Element("parameter").setAttribute("name","overwrite").setAttribute("value", "yes");
//            Element triggerE = new Element("trigger").addContent(parameter).setAttribute("event", "create,delete,update,copy,move").setAttribute("class", "org.exist.versioning.VersioningTrigger").setNamespace(ns);
//            triggers.add(triggerE);
//            String newConfig = new XMLOutputter(Format.getPrettyFormat()).outputString(doc);
//            String[] old = newConfig.split("\n");
//            List<String> fixedConfig = new ArrayList<>();
//            for (int i = 0; i<old.length; i++){
//                if(i != 0){
//                    fixedConfig.add(old[i]);
//                }
//            }
//            return existDBTriggerQueries.saveEditedTrigger(ExistDbCredentialsService.getDetails(), "/db/system/config/db/", String.join("\n", fixedConfig).replaceAll("\"","'")).contains("true");
//        }
//        else {
//            return false;
//        }
//    }
}
