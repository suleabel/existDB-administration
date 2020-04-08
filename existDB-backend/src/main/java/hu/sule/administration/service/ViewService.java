package hu.sule.administration.service;

import hu.sule.administration.model.ViewCreateModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface ViewService {

    void createViewTrigger(ViewCreateModel viewCreateModel);
    ArrayList<String> getDocs(String data);
    String genCondition(ArrayList<String> docs);
    void createAndSaveViewQuery(ViewCreateModel viewCreateModel, String trigger_name);
    void addTriggerToConfiguration(String trigger_name);
    void createViewLog(ViewCreateModel viewCreateModel, String trigger_name);
}
