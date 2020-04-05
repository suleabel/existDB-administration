package hu.sule.administration.service;

import hu.sule.administration.model.ExistDBGroup;
import org.jdom2.JDOMException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public interface GroupManagerService {
    ArrayList<ExistDBGroup> listGroups() throws JDOMException, IOException;
    String createGroup(ExistDBGroup group);
    String deleteGroup(String group);
    String editGroup(ExistDBGroup group);
    List<String> getGroupsName();
    ArrayList<ExistDBGroup> mapGroupsQueryResult(String input) throws JDOMException, IOException;
}
