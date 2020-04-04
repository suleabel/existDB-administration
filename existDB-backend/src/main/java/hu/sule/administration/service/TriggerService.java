package hu.sule.administration.service;
import hu.sule.administration.exceptions.CustomException;
import hu.sule.administration.model.EditTriggerModel;
import hu.sule.administration.model.ExistCollectionManagerModel;
import hu.sule.administration.model.ForStoreResourceAndColl;
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

    public void InitTriggerDir() {
        String triggerDir = "triggerQueries";
        if(!Arrays.asList(existDbCollectionManagerQueries.getCollectionContent(ExistDbCredentialsService.getDetails()
                , "/db").split("\n")).contains(triggerDir)){
            existDbCollectionManagerQueries.createCollection(ExistDbCredentialsService.getDetails(), new ForStoreResourceAndColl("/db", triggerDir));
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
                    existDbCollectionManagerQueries.createCollection(ExistDbCredentialsService.getDetails(), new ForStoreResourceAndColl(testedPath.toString(), part));
                }
                testedPath.append("/").append(part);
            }
        }
        System.out.println(path);
        return existDBTriggerQueries.initTriggerConfig(ExistDbCredentialsService.getDetails(), path);
    }

    public String addTrigger(EditTriggerModel editTriggerModel) {
        return addTriggerToConfiguration(editTriggerModel, editTriggerModel.getPath());
    }

    public String addTriggerToConfiguration(EditTriggerModel triggerModel, String url){
        SAXBuilder saxBuilder = new SAXBuilder();
        Namespace ns = Namespace.getNamespace("http://exist-db.org/collection-config/1.0");
        Document doc = null;
        try{
            doc = saxBuilder.build(new InputSource(new StringReader(collectionService.readFile(url + "/collection.xconf").getContent())));
        }catch (JDOMException e){
            throw new CustomException(e.getMessage(),"addTriggerToConfiguration","JDOMException", e.getStackTrace());
        }catch (IOException e){
            throw new CustomException(e.getMessage(),"addTriggerToConfiguration","IOException", e.getStackTrace());
        }
        if(doc != null) {

            Element collection = doc.getRootElement();
            List<Element> triggers = collection.getChildren().get(0).getChildren();
            Element parameterQuery = new Element("parameter").setNamespace(ns);
            Element parameterOverWrite = new Element("parameter").setNamespace(ns);
            Element triggerE = new Element("trigger").addContent(parameterQuery).addContent(parameterOverWrite).setNamespace(ns);
            if("true".equals(triggerModel.getIsOverwrite()))
                parameterOverWrite.setAttribute("name", "overwrite").setAttribute("value","yes");
            if(!"".equals(triggerModel.getName()))
                parameterQuery.setAttribute("name",triggerModel.getName());
            if(!"".equals(triggerModel.getValue()))
                parameterQuery.setAttribute("value", triggerModel.getValue());
            if(!"".equals(triggerModel.getEventByComma()))
                triggerE.setAttribute("event", triggerModel.getEventByComma());
            if(!"".equals(triggerModel.gettClass()))
                triggerE.setAttribute("class", triggerModel.gettClass());
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

    public ArrayList<ExistCollectionManagerModel> getTriggerConfiguration(String url) throws IOException {
        return collectionService.getFileManagerContentByCollection(url);
    }

    private boolean configIsAvailable(String path) {
        return existDBTriggerQueries.triggerConfigIsAvailable(ExistDbCredentialsService.getDetails(), path);
    }


    public String editTrigger(ForStoreResourceAndColl storeResource) {
        return existDBTriggerQueries.saveEditedTrigger(ExistDbCredentialsService.getDetails(), storeResource.getUrl(), String.join("\n", storeResource.getContent()).replaceAll("\"","'"));
    }
}
