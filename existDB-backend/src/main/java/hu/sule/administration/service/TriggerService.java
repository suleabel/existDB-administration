package hu.sule.administration.service;

import hu.sule.administration.model.EditTriggerModel;
import hu.sule.administration.model.ExistCollectionManagerModel;
import hu.sule.administration.model.ForStoreResourceAndColl;
import org.jdom2.JDOMException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public interface TriggerService {
    void InitTriggerDir();
    String initTriggerConfig(String path);
    String addTrigger(EditTriggerModel editTriggerModel);
    String addTriggerToConfiguration(EditTriggerModel triggerModel, String url);
    ArrayList<ExistCollectionManagerModel> getTriggerConfiguration(String url) throws IOException, JDOMException;
    boolean configIsAvailable(String path);
    String editTrigger(ForStoreResourceAndColl storeResource);
}
