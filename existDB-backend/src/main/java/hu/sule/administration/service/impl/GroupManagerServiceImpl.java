package hu.sule.administration.service.impl;

import hu.sule.administration.model.ExistDBGroup;
import hu.sule.administration.queries.ExistDbGroupManagerQueries;
import hu.sule.administration.service.GroupManagerService;
import hu.sule.administration.service.impl.ExistDbCredentialsServiceImpl;
import hu.sule.administration.util.Mappers;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
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
public class GroupManagerServiceImpl implements GroupManagerService {

    @Autowired
    private ExistDbGroupManagerQueries existDbGroupManagerQueries;

    public ArrayList<ExistDBGroup> listGroups() throws JDOMException, IOException {
        ArrayList<ExistDBGroup> groups = Mappers.mapGroupsQueryResult(existDbGroupManagerQueries.getGroups2(ExistDbCredentialsServiceImpl.getDetails()));
        for (ExistDBGroup group: groups) {
            group.setDefault(existDbGroupManagerQueries.isDefaultGroup(group.getGroupName()));
        }
        return groups;
    }

    public String createGroup(ExistDBGroup group) {
        return existDbGroupManagerQueries.createGroup(ExistDbCredentialsServiceImpl.getDetails(), group);
    }

    public String deleteGroup(String group) {
        return existDbGroupManagerQueries.deleteGroup(ExistDbCredentialsServiceImpl.getDetails(), group);
    }

    public String editGroup(ExistDBGroup group) {
        return existDbGroupManagerQueries.editGroup(ExistDbCredentialsServiceImpl.getDetails(), group);
    }

    public List<String> getGroupsName() {
        return Arrays.asList(existDbGroupManagerQueries.getGroupsNames(ExistDbCredentialsServiceImpl.getDetails()).split("\n"));
    }
}
