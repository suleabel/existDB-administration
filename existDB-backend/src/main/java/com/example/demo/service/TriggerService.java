package com.example.demo.service;
import com.example.demo.model.EditTriggerModel;
import com.example.demo.model.ExistFileManagerModel;
import com.example.demo.model.ForStoreResourceAndColl;
import com.example.demo.queries.ExistDBTriggerQueries;
import com.example.demo.queries.ExistDbCollectionManagerQueries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

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

    public String addTriggerToConfiguration(EditTriggerModel editTriggerModel){
        String trigger =
                "<trigger event=\"" + editTriggerModel.getEventByComma() + "\" class=\"" + editTriggerModel.gettClass() + "\">\n" +
                "\t\t\t<parameter name=\"" + editTriggerModel.getName() + "\" value=\"xmldb:exist://" + editTriggerModel.getValue() + "\"/>\n" +
                "\t\t</trigger>";
        System.out.println(trigger);
        String triggerConfigurationFile = existDbCollectionManagerQueries.readXmlFile(ExistDbCredentialsService.getDetails(), editTriggerModel.getPath() + "/" + editTriggerModel.getfName());
        System.out.println(triggerConfigurationFile);
        return "";
    }

    public ArrayList<ExistFileManagerModel> getTriggerConfiguration(String rootTriggerConfigs){
        return collectionService.getFileManagerContentByCollection(rootTriggerConfigs);
    }

    private boolean configIsAvailable(String path){
        return existDBTriggerQueries.triggerConfigIsAvailable(ExistDbCredentialsService.getDetails(), path);
    }
}
