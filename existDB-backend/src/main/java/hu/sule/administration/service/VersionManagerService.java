package hu.sule.administration.service;

import hu.sule.administration.exceptions.CustomeException;
import hu.sule.administration.model.*;
import hu.sule.administration.queries.ExistDbHistroyQueries;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class VersionManagerService {

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private ExistDbHistroyQueries existDbHistroyQueries;

    @Autowired
    private TriggerService triggerService;


    private static final Logger logger = LoggerFactory.getLogger(VersionManagerService.class);

    public String isEnabled() throws IOException{
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = null;
        try {
            String confContent = collectionService.readFile("/db/system/config/db/collection.xconf").getContent();
            if("".equals(confContent))
                throw new CustomeException("root collection.xconf is not exist, Please initialize it in the Trigger manager","","ERROR MESSAGE");
            doc = saxBuilder.build(new InputSource(new StringReader(confContent)));
        } catch(XMLDBException e){
            throw new CustomeException(e.getMessage(),"isEnabled in VersionManagerService","XMLDBException");
        } catch(JDOMException e){
            throw new CustomeException(e.getMessage(),"isEnabled in VersionManagerService","JDOMException");
        }
            if (doc != null) {
            Element collection = doc.getRootElement();
            List<Element> triggerList = collection.getChildren().get(0).getChildren();
            for (Element trigger : triggerList) {
                if (trigger.getAttributeValue("class").equals("org.exist.versioning.VersioningTrigger")) {
                    return "true";
                }
            }
        } else {
            return "false";
        }
        return "false";
    }

    public String enableVersioning() throws XMLDBException, JDOMException, IOException {
        List<String> events = new ArrayList<>();
        events.add("create");
        events.add("delete");
        events.add("update");
        events.add("copy");
        events.add("move");
//        if(triggerService.addTriggerToConfiguration(new TriggerModel(new ArrayList<>(),"org.exist.collections.triggers.HistoryTrigger","",""), "/db/system/config/db").equals("Failure!")){
//            return "Failure!";
//        }
        if(triggerService.addTriggerToConfiguration(new TriggerModel(events, "org.exist.versioning.VersioningTrigger", "overwrite", "yes"), "/db/system/config/db").equals("Failure!")){
            return "Failure!";
        }
        return "Success";
    }

    public VersionsModel getHistory(String path) throws JDOMException, IOException {
        return mapVersions(existDbHistroyQueries.getResHistroy(ExistDbCredentialsService.getDetails(), path));
    }

    public String getDiffByRev(VersionByRevModel versionByRevModel) throws IOException, JDOMException{
        return new XMLOutputter(Format.getPrettyFormat()).outputString(new SAXBuilder().build(new StringReader(existDbHistroyQueries.getDiffertencesByRev(ExistDbCredentialsService.getDetails(), versionByRevModel))));
    }

    public String resotreResByRev(VersionByRevModel versionByRevModel) throws IOException, JDOMException{
        return new XMLOutputter(Format.getPrettyFormat()).outputString(new SAXBuilder().build(new StringReader(existDbHistroyQueries.restoreDocByRev(ExistDbCredentialsService.getDetails(), versionByRevModel))));
    }

    private VersionsModel mapVersions(String input) throws JDOMException, IOException {
        VersionsModel versionsModel = new VersionsModel();
        ArrayList<ReversionsModel> reversionsModels = new ArrayList<>();
        ReversionsModel reversionsModel;
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = null;
        doc = saxBuilder.build(new InputSource(new StringReader(input)));
        if (doc != null) {
            Element root = doc.getRootElement();
            List<Element> data = root.getChildren();
            for (Element node : data) {
                if("document".equals(node.getName())){
                    versionsModel.setDoc(node.getValue());
                }
                if("revisions".equals(node.getName())){
                    List<Element> reversions = node.getChildren();
                    for (Element reversion : reversions) {
                        reversionsModel = new ReversionsModel(reversion.getAttributeValue("rev"),reversion.getChildren().get(0).getValue(),reversion.getChildren().get(1).getValue());
                        reversionsModels.add(reversionsModel);
                    }
                }
            }
            versionsModel.setReversions(reversionsModels);
        }
        return versionsModel;
    }
}
