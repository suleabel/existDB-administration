package hu.sule.administration.service.impl;

import hu.sule.administration.model.EditTriggerModel;
import hu.sule.administration.model.ForStoreResourceAndColl;
import hu.sule.administration.model.ViewCreateModel;
import hu.sule.administration.queries.ExistDbViewQueries;
import hu.sule.administration.service.CollectionService;
import hu.sule.administration.service.TriggerService;
import hu.sule.administration.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        //TODO meg lehetne keresni a meneneti doc ok legkissebb közös kollekcióját de nincs rá ötletem, hogy hogyan
        viewCreateModel.setQueryExpression(viewCreateModel.getQueryExpression().replaceAll("xquery version \"3.1\";",""));
        String triggerConfigLocation = "/db/system/config/db";
        String trigger_name = "/db/view_triggers/trigger_for_" + viewCreateModel.getViewName().replace(".xml", ".xql");
        ArrayList<String> docs = getDocs(viewCreateModel.getQueryExpression());
        List<String> events = new ArrayList<>();
        events.add("update");
        String ifCondition = genCondition(docs);
        collectionServiceImpl.Store(new ForStoreResourceAndColl("/db/view_triggers", trigger_name,existDbViewQueries.genViewTrigger(viewCreateModel, ifCondition),"application/xquery",true));
        triggerServiceImpl.addTrigger(new EditTriggerModel(triggerConfigLocation,"collection.xconf", events, "org.exist.collections.triggers.XQueryTrigger", "url",trigger_name,"false"));
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
        for (String doc: docs) {
            modDocs.add("$uri eq \"" + doc + "\"");
        }
        condition = String.join(" or ", modDocs);
        return condition;
    }
}
