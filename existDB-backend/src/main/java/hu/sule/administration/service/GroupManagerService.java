package hu.sule.administration.service;

import hu.sule.administration.model.ExistDBGroup;
import hu.sule.administration.model.ExistDBUser;
import hu.sule.administration.queries.ExistDbGroupManagerQueries;
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
public class GroupManagerService {

    @Autowired
    private ExistDbGroupManagerQueries existDbGroupManagerQueries;

    private static final Logger logger = LoggerFactory.getLogger(GroupManagerService.class);

    public ArrayList<ExistDBGroup> listGroups(){
        return mapGroupsQueryResult(existDbGroupManagerQueries.getGroups2(ExistDbCredentialsService.getDetails()));
    }

    public String createGroup(ExistDBGroup group) { return existDbGroupManagerQueries.createGroup(ExistDbCredentialsService.getDetails(), group); }

    public String deleteGroup(String group) { return existDbGroupManagerQueries.deleteGroup(ExistDbCredentialsService.getDetails(), group); }

    public String editGroup(ExistDBGroup group) { return existDbGroupManagerQueries.editGroup(ExistDbCredentialsService.getDetails(), group); }

    public List<String> getGroupsName() {
        return Arrays.asList(existDbGroupManagerQueries.getGroupsNames(ExistDbCredentialsService.getDetails()).split("\n"));
    }

    private ArrayList<ExistDBGroup> mapGroupsQueryResult(String input){
        ArrayList<ExistDBGroup> existDBGroups = new ArrayList<>();
        ExistDBGroup existDBGroup;
        ArrayList<String> groupMembers;
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = null;
        try {
            doc = saxBuilder.build(new InputSource(new StringReader(input)));
        } catch (JDOMException | IOException e){
            logger.error("SAXBuilder exception: " + e.getMessage()) ;
        }
        if(doc != null) {
            Element result = doc.getRootElement();
            List<Element> groups = result.getChildren();
            for(Element group: groups){
                groupMembers = new ArrayList<>();
                existDBGroup = new ExistDBGroup(group.getChildText("name"),group.getChildText("manager"),group.getChildText("desc"),existDbGroupManagerQueries.isDefaultGroup(group.getChildText("name")));
                Element members = group.getChild("groupMembers");
                List<Element> membersList = members.getChildren();
                for(Element member: membersList){
                    groupMembers.add(member.getValue());
                }
                existDBGroup.setGroupMembers(groupMembers);
                existDBGroups.add(existDBGroup);
            }
        }
        return existDBGroups;
    }
}
