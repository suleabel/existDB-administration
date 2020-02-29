package com.example.demo.service;

import com.example.demo.model.ExistDBGroup;
import com.example.demo.model.ExistDetails;
import com.example.demo.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ExistDbGroupManagerServices {

    private static Util util = new Util();
    private static final Logger logger = LoggerFactory.getLogger(ExistDbGroupManagerServices.class);

    public String createGroup(ExistDetails details, ExistDBGroup group){
        logger.info("try to create group, data: " + group.toString());
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        sm:create-group(\"" + group.getGroupName() + "\", \"" + group.getGroupManager() + "\", \"" + group.getDesc() + "\"),\n" +
                "        true()\n" +
                "    )\n" +
                "else\n" +
                "false()";
        if(groupExists(details, group.getGroupName()).equals("true")){
            return "Group is exist!";
        }
        util.stringResultQuery(details, query);
        return "Group created!";
    }

    public String deleteGroup(ExistDetails details, String groupName){
        logger.info("try to delete group: " + groupName);
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "("+
                "sm:remove-group(\"" + groupName + "\"),\n"+
                "true()\n" +
                ")" +
                "else\n" +
                "false()";
        if(groupExists(details, groupName).equals("true")){
            util.stringResultQuery(details, query);
            return "Groups is deleted!";
        }
        return "Groups is not exist!";
    }


    public String getGroups(ExistDetails details){
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"/db\",\"" + details.getUsername() + "\", \"" + details.getPassword() + "\" ,false())) then\n" +
                "    sm:list-groups()\n" +
                "else\n" +
                "\tfalse()";
        return util.stringResultQuery(details, query);
    }

    public String getGroupMembers(ExistDetails details, String groupName) {
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "sm:get-group-members(\"" + groupName + "\")\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String getGroupManager(ExistDetails details, String groupName) {
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "sm:get-group-managers(\"" + groupName + "\")\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String getGroupDesc(ExistDetails details, String groupName) {
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "declare variable $METADATA_DESCRIPTION_KEY := xs:anyURI(\"http://exist-db.org/security/description\");\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "sm:get-group-metadata(\"" + groupName + "\", $METADATA_DESCRIPTION_KEY)\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    private String groupExists(ExistDetails details, String group) {
        logger.info("check group is exist: " + group);
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\" , \"" + details.getUsername() + "\", \"" + details.getPassword() + "\")) then\n" +
                "sm:group-exists(\"" + group + "\")\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public boolean isDefaultGroup(String group) {
        return (group.equals("dba") || group.equals("eXide") || group.equals("guest") || group.equals("monex") || group.equals("nogroup") || group.equals("packageservice"));
    }

}
