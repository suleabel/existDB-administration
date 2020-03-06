package hu.sule.administration.service;

import hu.sule.administration.model.ExistDBGroup;
import hu.sule.administration.queries.ExistDbGroupManagerQueries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GroupManagerService {

    @Autowired
    private ExistDbGroupManagerQueries existDbGroupManagerQueries;

    private static final Logger logger = LoggerFactory.getLogger(GroupManagerService.class);

    public ArrayList<ExistDBGroup> listGroups(){

        List<String> groups;
        ArrayList<ExistDBGroup> dbGroups = new ArrayList<>();
        groups = Arrays.asList(existDbGroupManagerQueries.getGroups(ExistDbCredentialsService.getDetails()).split("\n"));

        for (String group: groups) {
            ExistDBGroup group1 = new ExistDBGroup();
            group1.setGroupName(group);
            group1.setGroupManager(existDbGroupManagerQueries.getGroupManager(ExistDbCredentialsService.getDetails(), group));
            group1.setDesc(existDbGroupManagerQueries.getGroupDesc(ExistDbCredentialsService.getDetails(), group));
            group1.setGroupMembers(Arrays.asList(existDbGroupManagerQueries.getGroupMembers(ExistDbCredentialsService.getDetails(), group).split("\n")));
            group1.setDefault(existDbGroupManagerQueries.isDefaultGroup(group));
            dbGroups.add(group1);
        }
        return dbGroups;
    }

    public String createGroup(ExistDBGroup group) { return existDbGroupManagerQueries.createGroup(ExistDbCredentialsService.getDetails(), group); }

    public String deleteGroup(String group) { return existDbGroupManagerQueries.deleteGroup(ExistDbCredentialsService.getDetails(), group); }

    public String editGroup(ExistDBGroup group) { return existDbGroupManagerQueries.editGroup(ExistDbCredentialsService.getDetails(), group); }

    public List<String> getGroupsName() {
        return Arrays.asList(existDbGroupManagerQueries.getGroups(ExistDbCredentialsService.getDetails()).split("\n"));
    }
}
