package hu.sule.administration.service;

import hu.sule.administration.model.EditTriggerModel;
import hu.sule.administration.model.ForStoreResourceAndColl;
import hu.sule.administration.model.ViewCreateModel;
import hu.sule.administration.queries.ExistDbViewQueries;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ViewService {

    @Autowired
    private ExistDbViewQueries existDbViewQueries;

    @Autowired
    private TriggerService triggerService;

    @Autowired
    private CollectionService collectionService;

    public void createViewTrigger(ViewCreateModel viewCreateModel)throws IOException, JDOMException, XMLDBException {
        //TODO meg lehetne keresni a meneneti doc ok legkissebb tözös kollekcióját de nincs rá ötletem, hogy hogyan
        String triggerConfigLocation = "/db/system/config/db";
        String trigger_name = "/db/view_triggers/trigger_for_" + viewCreateModel.getViewName().replace(".xml", ".xql");
        ArrayList<String> docs = getDocs(viewCreateModel.getQueryExpression());
        List<String> events = new ArrayList<>();
        events.add("update");
        String ifCondition = genCondition(docs);
        collectionService.Store(new ForStoreResourceAndColl("/db/view_triggers", trigger_name,existDbViewQueries.genViewTrigger(viewCreateModel, ifCondition),"application/xquery",true));
        triggerService.addTrigger(new EditTriggerModel(triggerConfigLocation,"collection.xconf", events, "org.exist.collections.triggers.XQueryTrigger", "url",trigger_name));
}

    private ArrayList<String> getDocs(String data){
        ArrayList<String> docs = new ArrayList<>();
        String regex = "(?<=doc\\(\")(.*?)(?=\"\\))";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(data);
        while (matcher.find()) {
            docs.add(matcher.group());
        }
        return docs;
    }

    private String genCondition(ArrayList<String> docs){
        String condition = "";
        List<String> modDocs = new ArrayList<>();
        for (String doc: docs) {
            modDocs.add("$uri eq \"" + doc + "\"");
        }
        condition = String.join(" or ", modDocs);
        return condition;
    }
}
