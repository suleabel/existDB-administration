package hu.sule.administration.service.impl;

import hu.sule.administration.exceptions.ApiException;
import hu.sule.administration.model.CreatedViewModel;
import hu.sule.administration.model.EditTriggerModel;
import hu.sule.administration.model.ForStoreResourceAndColl;
import hu.sule.administration.model.ViewCreateModel;
import hu.sule.administration.queries.ExistDbViewQueries;
import hu.sule.administration.service.CollectionService;
import hu.sule.administration.service.TriggerService;
import hu.sule.administration.service.ViewService;
import hu.sule.administration.util.Mappers;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ViewServiceImpl implements ViewService {

    private ExistDbViewQueries existDbViewQueries;
    private TriggerService triggerServiceImpl;
    private CollectionService collectionServiceImpl;

    @Autowired
    public void setExistDbViewQueries(ExistDbViewQueries existDbViewQueries) {
        this.existDbViewQueries = existDbViewQueries;
    }
    @Autowired
    public void setTriggerServiceImpl(TriggerService triggerServiceImpl) {
        this.triggerServiceImpl = triggerServiceImpl;
    }
    @Autowired
    public void setCollectionServiceImpl(CollectionService collectionServiceImpl) {
        this.collectionServiceImpl = collectionServiceImpl;
    }

    @Override
    public void createViewTrigger(ViewCreateModel viewCreateModel) {
        String trigger_name = "/db/view_triggers/trigger_for_" + viewCreateModel.getViewName().replace(".xml", ".xql");
        createAndSaveViewQuery(viewCreateModel, trigger_name);
        addTriggerToConfiguration(trigger_name);
        createViewLog(viewCreateModel, trigger_name);

    }
    @Override
    public void createAndSaveViewQuery(ViewCreateModel viewCreateModel, String trigger_name){
        viewCreateModel.setQueryExpression(viewCreateModel.getQueryExpression().replaceAll("xquery version \"3.1\";",""));
        String ifCondition = genCondition(getDocs(viewCreateModel.getQueryExpression()));
        String  viewTriggerLocation = "/db/view_triggers";
        if(!collectionServiceImpl.collectionIsAvailable(viewTriggerLocation)){
            collectionServiceImpl.createDir(new ForStoreResourceAndColl("/db","view_triggers"));
        }
        collectionServiceImpl.Store(new ForStoreResourceAndColl(viewTriggerLocation, trigger_name,existDbViewQueries.genViewTrigger(viewCreateModel, ifCondition),"application/xquery",true));
    }
    @Override
    public void addTriggerToConfiguration(String trigger_name){
        String triggerConfigLocation = "/db/system/config/db";
        List<String> events = new ArrayList<>();
        events.add("update");
        if(collectionServiceImpl.resourceIsAvailable(triggerConfigLocation + "/collection.xconf")) {
            triggerServiceImpl.addTrigger(new EditTriggerModel(triggerConfigLocation,"collection.xconf", events, "org.exist.collections.triggers.XQueryTrigger", "url",trigger_name));
        } else {
            throw new ApiException("Target collection.xconf is not available, Please initialize it in trigger manager","createViewTrigger","null");
        }
    }
    @Override
    public void createViewLog(ViewCreateModel viewCreateModel, String trigger_name){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String triggerConfigLocation = "/db/system/config/db";
        if(!existDbViewQueries.logViewCreation(ExistDbCredentialsServiceImpl.getDetails() ,viewCreateModel, triggerConfigLocation, trigger_name, ExistDbCredentialsServiceImpl.getDetails().getUsername(), dateFormat.format(date))){
            throw new ApiException("view creation log is not succeeded","createViewTrigger","null");
        }
    }

    @Override
    public ArrayList<CreatedViewModel> getCreatedViews() throws JDOMException, IOException {
        return Mappers.mapCreatedViewModel(collectionServiceImpl.readFile("/db/createdViews.xml").getContent());
    }

    @Override
    public ArrayList<String> getDocs(String data){
        ArrayList<String> docs = new ArrayList<>();
        String regex = "(?<=doc\\(\")(.*?)(?=\"\\))";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(data);
        while (matcher.find()) {
            docs.add(matcher.group());
        }
        return docs;
    }

    @Override
    public String genCondition(ArrayList<String> docs){
        String condition = "";
        List<String> modDocs = new ArrayList<>();
        if(docs.isEmpty()){
            condition = "true()";
        }else {
            for (String doc: docs) {
                modDocs.add("$uri eq \"" + doc + "\"");
            }
            condition = String.join(" or ", modDocs);
        }
        return condition;
    }
}
