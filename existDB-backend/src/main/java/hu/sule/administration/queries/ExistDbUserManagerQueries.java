package hu.sule.administration.queries;

import hu.sule.administration.model.ExistDBUser;
import hu.sule.administration.model.ExistDetails;
import hu.sule.administration.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;

@Component
public class ExistDbUserManagerQueries {

    private Collection collection = null;

    private static final Logger logger = LoggerFactory.getLogger(ExistDbUserManagerQueries.class);

    private static Util util = new Util();

    public String createUser(ExistDetails details, ExistDBUser user) throws XMLDBException {
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

    //TODO meg kéne nézni hogy a change pass része működik-e
    public String editUser(ExistDetails details, ExistDBUser user) throws XMLDBException {
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
                "        if(string-length($password) ne 0) then " +
                "            sm:passwd($user, $password) else (),\n" +
                "            true()\n" +
                "        )\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }


    public String removeUserFromGroup(ExistDetails details, String user, String group) throws XMLDBException {
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        sm:remove-group-member(\"" + group + "\", \"" + user + "\")\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String addUserToGroup(ExistDetails details, String user, String group) throws XMLDBException {
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        sm:add-group-member(\"" + group + "\", \"" + user + "\")\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String getUsersData(ExistDetails details) throws XMLDBException {
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "declare variable $METADATA_FULLNAME_KEY := xs:anyURI(\"http://axschema.org/namePerson\");\n" +
                "declare variable $METADATA_DESCRIPTION_KEY := xs:anyURI(\"http://exist-db.org/security/description\");\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        <users>\n" +
                "            {for $user in sm:list-users()return\n" +
                "                (\n" +
                "                <user>\n" +
                "                    <username>{$user}</username>\n" +
                "                    <primaryGroups>{sm:get-user-primary-group($user)}</primaryGroups>\n" +
                "                    <fullName>{sm:get-account-metadata($user, $METADATA_FULLNAME_KEY)}</fullName>\n" +
                "                    <groups>\n" +
                "                        {for $group in sm:get-user-groups($user) return(<group>{$group}</group>)}\n" +
                "                    </groups>\n" +
                "                    <umask>{sm:get-umask($user)}</umask>\n" +
                "                    <desc>{sm:get-account-metadata($user, $METADATA_DESCRIPTION_KEY)}</desc>\n" +
                "                    <isEnabled>{sm:is-account-enabled($user)}</isEnabled>\n" +
                "                </user>\n" +
                "                )\n" +
                "            }\n" +
                "        </users>\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String getUsersNames(ExistDetails details) throws XMLDBException {
        logger.info("getAllUsers");
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"/db\",\"" + details.getUsername() + "\", \"" + details.getPassword() + "\" ,false())) then\n" +
                "    sm:list-users()\n" +
                "else\n" +
                "\tfalse()";
        return util.stringResultQuery(details, query);
    }

    public String deleteUser(ExistDetails details, String username) throws XMLDBException {
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

    public String changeUserPass(ExistDetails details, String username, String password) throws XMLDBException {
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

    private String userExists(ExistDetails details, String user) throws XMLDBException {
        logger.info("check user is exist: " + user);
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\" , \"" + details.getUsername() + "\", \"" + details.getPassword() + "\")) then\n" +
                "sm:user-exists(\"" + user + "\")\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public boolean isAdminAccess(ExistDetails details) throws XMLDBException {
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
