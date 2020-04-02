package hu.sule.administration.queries;

import hu.sule.administration.model.ExistDBGroup;
import hu.sule.administration.model.ExistDetails;
import hu.sule.administration.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xmldb.api.base.XMLDBException;

@Component
public class ExistDbGroupManagerQueries {

    private static Util util = new Util();
    private static final Logger logger = LoggerFactory.getLogger(ExistDbGroupManagerQueries.class);

    public String createGroup(ExistDetails details, ExistDBGroup group) {
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

    public String deleteGroup(ExistDetails details, String groupName) {
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

    public String getGroups2(ExistDetails details) {
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "declare variable $METADATA_DESCRIPTION_KEY := xs:anyURI(\"http://exist-db.org/security/description\");\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        <groups>\n" +
                "            {for $group in sm:list-groups()return\n" +
                "                (\n" +
                "                <group>\n" +
                "                    <name>{$group}</name>\n" +
                "                    <manager>{sm:get-group-managers($group)}</manager>\n" +
                "                    <desc>{sm:get-group-metadata($group, $METADATA_DESCRIPTION_KEY)}</desc>\n" +
                "                    <groupMembers>\n" +
                "                        {for $member in sm:get-group-members($group)return\n" +
                "                            (\n" +
                "                                <member>{$member}</member>\n" +
                "                            )\n" +
                "                        }\n" +
                "                    </groupMembers>\n" +
                "                </group>\n" +
                "                )\n" +
                "            }\n" +
                "        </groups>\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String getGroupsNames(ExistDetails details) {
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"/db\",\"" + details.getUsername() + "\", \"" + details.getPassword() + "\" ,false())) then\n" +
                "    sm:list-groups()\n" +
                "else\n" +
                "\tfalse()";
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

    public String editGroup(ExistDetails details, ExistDBGroup group) {
        System.out.println(group);
        return "";
    }


    public boolean isDefaultGroup(String group) {
        return (group.equals("dba") || group.equals("eXide") || group.equals("guest") || group.equals("monex") || group.equals("nogroup") || group.equals("packageservice"));
    }

}
