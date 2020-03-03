package com.example.demo.queries;

import com.example.demo.model.ExistDBUsers;
import com.example.demo.model.ExistDetails;
import com.example.demo.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;

@Component
public class ExistDbUserManagerQueries {

    private Collection collection = null;

    private static final Logger logger = LoggerFactory.getLogger(ExistDbUserManagerQueries.class);

    private static Util util = new Util();

    public String createUser(ExistDetails details, ExistDBUsers user) {
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        sm:create-account(\"" + user.getUsername() + "\", \"" + user.getPassword() + "\", \"" + user.getPrimaryGroup() + "\", (\"" + user.getGroupsAsString() + "\"), \"" + user.getFullName() + "\", \"" + user.getDesc() + "\"),\n" +
                "        true()\n" +
                "    )\n" +
                "else\n" +
                "false()";
        if (userExists(details, user.getUsername()).equals("true")) {
            return "User is exist!";

        }
        util.stringResultQuery(details, query);
        return "User created!";
    }

    public String editUser(ExistDetails details, ExistDBUsers user) {
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "declare variable $METADATA_FULLNAME_KEY := xs:anyURI(\"http://axschema.org/namePerson\");\n" +
                "declare variable $METADATA_DESCRIPTION_KEY := xs:anyURI(\"http://exist-db.org/security/description\");\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        let $user := \"" + user.getUsername() + "\",\n" +
                "        $fullName := \"" + user.getFullName() + "\",\n" +
                "        $description := \"" + user.getDesc() + "\",\n" +
                "        $password := \"" + user.getPassword() + "\",\n" +
                "        $primaryGroup := \"" + user.getPrimaryGroup() + "\",\n" +
                "        $disabled := xs:boolean(\"" + user.isEnabled() + "\"),\n" +
                "        $umask := xs:integer(" + user.getUmask() + ")\n" +
                "        return (\n" +
                "            if(sm:get-account-metadata($user, $METADATA_FULLNAME_KEY) = $fullName)then\n" +
                "        ()else \n" +
                "            sm:set-account-metadata($user, $METADATA_FULLNAME_KEY, $fullName),\n" +
                "        if(sm:get-account-metadata($user, $METADATA_DESCRIPTION_KEY) = $description)then\n" +
                "        ()else \n" +
                "            sm:set-account-metadata($user, $METADATA_DESCRIPTION_KEY, $description),\n" +
                "        if(sm:is-account-enabled($user) eq $disabled)then\n" +
                "            sm:set-account-enabled($user, $disabled)\n" +
                "        else(),\n" +
                "        if(sm:get-user-primary-group($user) ne $primaryGroup)then\n" +
                "            sm:set-user-primary-group($user, $primaryGroup)\n" +
                "        else(),\n" +
                "        if(sm:get-umask($user) ne $umask)then\n" +
                "            sm:set-umask($user, $umask)\n" +
                "        else(),\n" +
                "        if($password ne \"null\") then sm:passwd($user, $password) else (),\n" +
                "            true()\n" +
                "        )\n" +
                "    )\n" +
                "else\n" +
                "false()";
        editUserGroups(details, user);
        return util.stringResultQuery(details, query);
    }

    public String editUserGroups(ExistDetails details, ExistDBUsers user) {
        user.getGroups().remove(user.getPrimaryGroup());
        System.out.println("NOT FINISHED FUNCTION, user groups after remove pg: " + user.getGroups());
        String query = "";
        return "";
    }


    public boolean isAccountEnabled(ExistDetails details, String username) {
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\" , \"" + details.getUsername() + "\", \"" + details.getPassword() + "\")) then\n" +
                "sm:is-account-enabled(\"" + username + "\")\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query).equals("true");

    }

    public String getUsers(ExistDetails details) {
        logger.info("getAllUsers");
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"/db\",\"" + details.getUsername() + "\", \"" + details.getPassword() + "\" ,false())) then\n" +
                "    sm:list-users()\n" +
                "else\n" +
                "\tfalse()";
        return util.stringResultQuery(details, query);
    }

    public String deleteUser(ExistDetails details, String username) {
        logger.info("Try to create user: " + username);
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "(" +
                "sm:remove-account(\"" + username + "\"),\n" +
                "true()\n" +
                ")" +
                "else\n" +
                "false()";
        if (userExists(details, username).equals("true")) {
            util.stringResultQuery(details, query);
            return "User is deleted!";
        }
        return "User is not exist!";
    }

    public String getUserGroups(ExistDetails details, String user) {
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\",false())) then\n" +
                "    sm:get-user-groups(\"" + user + "\")\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String changeUserPass(ExistDetails details, String username, String password) {
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "(\n" +
                "sm:passwd(\"" + username + "\", \"" + password + "\"),\n" +
                "true()\n" +
                ")\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String getUserUmask(ExistDetails details, String user) {
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\",false())) then\n" +
                "    sm:get-umask(\"" + user + "\")\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String getUserFullname(ExistDetails details, String user) {
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "declare variable $METADATA_FULLNAME_KEY := xs:anyURI(\"http://axschema.org/namePerson\");\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\",false())) then\n" +
                "sm:get-account-metadata(\"" + user + "\", $METADATA_FULLNAME_KEY)\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String getUserDesc(ExistDetails details, String user) {
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "declare variable $METADATA_DESCRIPTION_KEY := xs:anyURI(\"http://exist-db.org/security/description\");" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\",false())) then\n" +
                "sm:get-account-metadata(\"" + user + "\", $METADATA_DESCRIPTION_KEY)\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String getUserPrimaryGroup(ExistDetails details, String user) {
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\",false())) then\n" +
                "    sm:get-user-primary-group(\"" + user + "\")\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    private String userExists(ExistDetails details, String user) {
        logger.info("check user is exist: " + user);
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\" , \"" + details.getUsername() + "\", \"" + details.getPassword() + "\")) then\n" +
                "sm:user-exists(\"" + user + "\")\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public boolean isAndminAccess(ExistDetails details) throws Exception {

        logger.info("check admin access");

        Collection old = collection;
        boolean result = false;
        util.closeCollection(collection);
        collection = DatabaseManager.getCollection(details.getUrl() + details.getCollection());
        String querry = "xquery version \"3.1\";\n" +
                "import module namespace xmldb=\"http://exist-db.org/xquery/xmldb\" at \"java:org.exist.xquery.functions.xmldb.XMLDBModule\";\n" +
                "if(xmldb:login(\"/db\",\"" + details.getUsername() + "\", \"" + details.getPassword() + "\" ))then\n" +
                "sm:is-dba(\"" + details.getUsername() + "\")\n" +
                "else\n" +
                "false()";
        result = !util.execXQuery(querry, collection).equals("false");
        util.closeCollection(collection);
        collection = old;
        logger.info("isAdminAccess: " + result);
        return result;
    }


    public boolean isDefaultUser(String user) {
        return (user.equals("SYSTEM") || user.equals("admin") || user.equals("eXide") || user.equals("guest") || user.equals("monex") || user.equals("nobody") || user.equals("packageservice"));
    }
}
