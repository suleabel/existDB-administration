package hu.sule.administration.service.impl;

import hu.sule.administration.exceptions.CustomException;
import hu.sule.administration.model.*;
import hu.sule.administration.queries.ExistDbVersionManagementQueries;
import hu.sule.administration.service.CollectionService;
import hu.sule.administration.service.TriggerService;
import hu.sule.administration.service.VersionManagerService;
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

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class VersionManagerServiceImpl implements VersionManagerService {
    private CollectionService collectionServiceImpl;
    private ExistDbVersionManagementQueries existDbVersionManagementQueries;
    private TriggerService triggerServiceImpl;

    @Autowired
    public void setCollectionServiceImpl(CollectionService collectionServiceImpl) {
        this.collectionServiceImpl = collectionServiceImpl;
    }

    @Autowired
    public void setExistDbVersionManagementQueries(ExistDbVersionManagementQueries existDbVersionManagementQueries) {
        this.existDbVersionManagementQueries = existDbVersionManagementQueries;
    }

    @Autowired
    public void setTriggerServiceImpl(TriggerService triggerServiceImpl) {
        this.triggerServiceImpl = triggerServiceImpl;
    }

    private static final Logger logger = LoggerFactory.getLogger(VersionManagerServiceImpl.class);

    public String isEnabled() throws IOException{
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc;
        try {
            String confContent = collectionServiceImpl.readFile("/db/system/config/db/collection.xconf").getContent();
            if("".equals(confContent))
                throw new CustomException("root collection.xconf is not exist, Please initialize it in the Trigger manager","","ERROR MESSAGE");
            doc = saxBuilder.build(new InputSource(new StringReader(confContent)));
        } catch(JDOMException e){
            throw new CustomException(e.getMessage(),"isEnabled in VersionManagerService","JDOMException", e.getStackTrace());
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

    public String enableVersioning() {
        List<String> events = new ArrayList<>();
        events.add("create");
        events.add("delete");
        events.add("update");
        events.add("copy");
        events.add("move");
//        if(triggerService.addTriggerToConfiguration(new TriggerModel(new ArrayList<>(),"org.exist.collections.triggers.HistoryTrigger","",""), "/db/system/config/db").equals("Failure!")){
//            return "Failure!";
//        }
        if(triggerServiceImpl.addTriggerToConfiguration(new EditTriggerModel(events, "org.exist.versioning.VersioningTrigger", "overwrite", "yes"), "/db/system/config/db").equals("Failure!")){
            return "Failure!";
        }
        return "Success";
    }

    public VersionsModel getHistory(String path) throws JDOMException, IOException {
        return mapVersions(existDbVersionManagementQueries.getResHistory(ExistDbCredentialsServiceImpl.getDetails(), path));
    }

    public String getDiffByRev(VersionByRevModel versionByRevModel) throws IOException, JDOMException{
        return new XMLOutputter(Format.getPrettyFormat()).outputString(new SAXBuilder().build(new StringReader(existDbVersionManagementQueries.getDifferencesByRev(ExistDbCredentialsServiceImpl.getDetails(), versionByRevModel))));
    }

    public String resotreResByRev(VersionByRevModel versionByRevModel) throws IOException, JDOMException{
        return new XMLOutputter(Format.getPrettyFormat()).outputString(new SAXBuilder().build(new StringReader(existDbVersionManagementQueries.restoreDocByRev(ExistDbCredentialsServiceImpl.getDetails(), versionByRevModel))));
    }

    public VersionsModel mapVersions(String input) throws JDOMException, IOException {
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