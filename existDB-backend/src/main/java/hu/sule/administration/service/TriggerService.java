package hu.sule.administration.service;
import hu.sule.administration.model.EditTriggerModel;
import hu.sule.administration.model.ExistFileManagerModel;
import hu.sule.administration.model.ForStoreResourceAndColl;
import hu.sule.administration.model.TriggerModel;
import hu.sule.administration.queries.ExistDBTriggerQueries;
import hu.sule.administration.queries.ExistDbCollectionManagerQueries;
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
import java.util.Arrays;
import java.util.List;

@Service
public class TriggerService {

    @Autowired
    ExistDbCollectionManagerQueries existDbCollectionManagerQueries;

    @Autowired
    ExistDBTriggerQueries existDBTriggerQueries;

    @Autowired
    CollectionService collectionService;

    private static final Logger logger = LoggerFactory.getLogger(TriggerService.class);

    public void InitTriggerDir(){
        String triggerDir = "triggerQueries";
        if(!Arrays.asList(existDbCollectionManagerQueries.getCollectionContent(ExistDbCredentialsService.getDetails()
                , "/db").split("\n")).contains(triggerDir)){
            existDbCollectionManagerQueries.createCollection(ExistDbCredentialsService.getDetails(), new ForStoreResourceAndColl("/db", triggerDir, null));
            logger.info("Trigger Root Directory initializing");
        }
    }

    public String initTriggerConfig(String path) {
        String configRoot = "/db/system/config";
        StringBuilder testedPath = new StringBuilder(configRoot);
        String[] pathParts = path.split("/");
        for (String part: pathParts) {
            if(!part.equals("")){
                if(!configIsAvailable(testedPath.toString() + "/" + part)){
                    existDbCollectionManagerQueries.createCollection(ExistDbCredentialsService.getDetails(), new ForStoreResourceAndColl(testedPath.toString(), part, null));
                    System.out.println("not available path: " + testedPath + " part: " + part);
                }
                testedPath.append("/").append(part);
            }
        }
        return existDBTriggerQueries.initTriggerConfig(ExistDbCredentialsService.getDetails(), path);
    }

    public String addTrigger(EditTriggerModel editTriggerModel){
        return addTriggerToConfiguration(editTriggerModel, editTriggerModel.getPath());
    }

    public String addTriggerToConfiguration(TriggerModel triggerModel, String url){
        System.out.println(url + "collection.xconf");
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = null;
        Namespace ns = Namespace.getNamespace("http://exist-db.org/collection-config/1.0");
        try {
            doc = saxBuilder.build(new InputSource(new StringReader(collectionService.readFile(url + "/collection.xconf").getContent())));
        } catch (JDOMException | IOException e){
            logger.error("SAXBuilder exception: " + e.getMessage()) ;
        }
        if(doc != null) {

            Element collection = doc.getRootElement();
            List<Element> triggers = collection.getChildren().get(0).getChildren();
            Element parameter = new Element("parameter").setAttribute("name",triggerModel.getName()).setAttribute("value", triggerModel.getValue()).setNamespace(ns);
            Element triggerE = new Element("trigger").addContent(parameter).setAttribute("event", triggerModel.getEventByComma()).setAttribute("class", triggerModel.gettClass()).setNamespace(ns);
            triggers.add(triggerE);
            String newConfig = new XMLOutputter(Format.getPrettyFormat()).outputString(doc);
            String[] old = newConfig.split("\n");
            List<String> fixedConfig = new ArrayList<>();
            for (int i = 0; i<old.length; i++){
                if(i != 0){
                    fixedConfig.add(old[i]);
                }
            }
            return existDBTriggerQueries.saveEditedTrigger(ExistDbCredentialsService.getDetails(), url, String.join("\n", fixedConfig).replaceAll("\"","'"));
        }
        else {
            return "Failure!";
        }
    }

    public List<TriggerModel> getTriggers(String url) {
        List<TriggerModel> triggersList = new ArrayList<>();
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = null;
        try {
            doc = saxBuilder.build(new InputSource(new StringReader(collectionService.readFile(url).getContent())));
        } catch (JDOMException | IOException e){
            logger.error("SAXBuilder exception: " + e.getMessage()) ;
        }
        if(doc != null) {
            Element collection = doc.getRootElement();
            List<Element> triggers = collection.getChildren().get(0).getChildren();
            for (Element trigger: triggers) {
                Element parameter = trigger.getChild("parameter");
                TriggerModel triggerModel = new TriggerModel();
                if(trigger.getAttributeValue("event") != null){
                    triggerModel.setEvent(Arrays.asList(trigger.getAttributeValue("event").split(",")));
                } else {
                    triggerModel.setEvent(new ArrayList<>());
                }
                if(trigger.getAttributeValue("class") != null){
                    triggerModel.settClass(trigger.getAttributeValue("class"));
                }
                else {
                    triggerModel.settClass("");
                }
                if(parameter != null){
                    if(parameter.getAttributeValue("name") != null){
                        triggerModel.setName(parameter.getAttributeValue("name"));
                    }else{
                        triggerModel.setName("");
                    }
                    if(parameter.getAttributeValue("value") != null){
                       triggerModel.setValue(parameter.getAttributeValue("value"));
                    }else{
                        triggerModel.setValue("");
                    }
                }
                triggersList.add(triggerModel);
            }
        }
        System.out.println(triggersList);
        return triggersList;
    }

    public ArrayList<ExistFileManagerModel> getTriggerConfiguration(String url){
        return collectionService.getFileManagerContentByCollection(url);
    }

    private boolean configIsAvailable(String path){
        return existDBTriggerQueries.triggerConfigIsAvailable(ExistDbCredentialsService.getDetails(), path);
    }


}
