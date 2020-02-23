package com.example.demo.service;

import com.example.demo.model.ExistDBUsers;
import com.example.demo.model.ExistDetails;
import com.example.demo.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;

@Component
public class ExistDbUserManagerServices {

    private Collection collection = null;

    private static final Logger logger = LoggerFactory.getLogger(ExistDbUserManagerServices.class);

    private static Util util = new Util();

    public String createUser(ExistDetails details, ExistDBUsers user){
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        sm:create-account(\"" + user.getUsername() + "\", \"" + user.getPassword() + "\", \"" + user.getPrimaryGroup() + "\", (\""+ user.getGroupsAsString() +"\"), \"" + user.getFullName() + "\", \"" + user.getDesc() + "\"),\n" +
                "        true()\n" +
                "    )\n" +
                "else\n" +
                "false()";
        if(userExists(details, user.getUsername()).equals("true")){
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
                "        $disabled := \"" + user.isEnabled() + "\",\n" +
                "        $umask := \"" + user.getUmask() + "\",\n" +
                "        $groups := [\"" + user.getGroupsAsString() + "\"]\n" +
                "                return (\n" +
                "            if(secman:get-account-metadata($user, $METADATA_FULLNAME_KEY) = $fullName)then\n" +
                "                    ()\n" +
                "                else secman:set-account-metadata($user, $METADATA_FULLNAME_KEY, $fullName)\n" +
                "                ,\n" +
                "            if(secman:get-account-metadata($user, $METADATA_DESCRIPTION_KEY) = $description)then\n" +
                "                    ()\n" +
                "                else secman:set-account-metadata($user, $METADATA_DESCRIPTION_KEY, $description)\n" +
                "                ,\n" +
                "            if(secman:is-account-enabled($user) eq $disabled)then\n" +
                "                    secman:set-account-enabled($user, $disabled)\n" +
                "                else(),\n" +
                "            if(secman:get-umask($user) ne $umask)then\n" +
                "                    secman:set-umask($user, $umask)\n" +
                "                else(),\n" +
                "                for $group in secman:get-user-groups($user) return secman:remove-group-member($group, $user),\n" +
                "                for $group in $groups return if(secman:group-exists($group)) then secman:add-group-member($group, $user) else (),\n" +
                "                if($password) then secman:passwd($user, $password) else (),\n" +
                "\n" +
                "                true()\n" +
                "            )" +
                "    )\n" +
                "else\n" +
                "false()";
        System.out.println("query: " + query);
        System.out.println(util.stringResultQuery(details, query));
        return "test";
    }


    public String enableDisableAccount(){
        String query = "sm:set-account-enabled($username as xs:string, $enabled as xs:boolean)";
        return "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "declare variable $METADATA_FULLNAME_KEY := xs:anyURI(\"http://axschema.org/namePerson\");\n" +
                "declare variable $METADATA_DESCRIPTION_KEY := xs:anyURI(\"http://exist-db.org/security/description\");\n" +
                "if(xmldb:login(\"/db\",\"admin\",\"admin1234\")) then\n" +
                "    (\n" +
                "        let $user := \"\",\n" +
                "        $fullName := \"\",\n" +
                "        $description := \"\",\n" +
                "        $password := \"\",\n" +
                "        $disabled := false,\n" +
                "        $umask := \"\",\n" +
                "        $groups := [][@type eq \"array\"]/item/string(text())\n" +
                "        return (\"$user\",\"$fullName\",\"$description\",\"$password\",\"$disabled\",\"$umask\",\"$groups\")\n" +
                "    )\n" +
                "else\n" +
                "false()";
    }

    public boolean isAccountEnabled(ExistDetails details, String username){
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\" , \"" + details.getUsername() + "\", \"" + details.getPassword() + "\")) then\n" +
                "sm:is-account-enabled(\"" + username + "\")\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query).equals("true");

    }

    public String getUsers(ExistDetails details){
        logger.info("getAllUsers");
            String query = "xquery version \"3.1\";\n" +
                    "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                    "if(xmldb:login(\"/db\",\"" + details.getUsername() + "\", \"" + details.getPassword() + "\" ,false())) then\n" +
                    "    sm:list-users()\n" +
                    "else\n" +
                    "\tfalse()";
        return util.stringResultQuery(details,query);
    }

    public String deleteUser(ExistDetails details, String username){
        logger.info("Try to create user: " + username);
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "("+
                "sm:remove-account(\"" + username + "\"),\n"+
                "true()\n" +
                ")" +
                "else\n" +
                "false()";
        if(userExists(details, username).equals("true")){
            util.stringResultQuery(details, query);
            return "User is deleted!";
        }
        return "User is not exist!";
    }

    public String getUserGroups(ExistDetails details, String user){
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\",false())) then\n" +
                "    sm:get-user-groups(\"" + user + "\")\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details,query);
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
        return util.stringResultQuery(details,query);
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

    public boolean isAndminAccess(ExistDetails details){

        logger.info("check admin access");

        Collection old = collection;
        boolean result = false;
        try{
            util.closeCollection(collection);
            collection = DatabaseManager.getCollection(details.getUrl() + details.getCollection());
            String querry = "xquery version \"3.1\";\n" +
                    "import module namespace xmldb=\"http://exist-db.org/xquery/xmldb\" at \"java:org.exist.xquery.functions.xmldb.XMLDBModule\";\n" +
                    "if(xmldb:login(\"/db\",\"" + details.getUsername() + "\", \"" + details.getPassword() + "\" ))then\n" +
                    "sm:is-dba(\""+ details.getUsername() +"\")\n" +
                    "else\n" +
                    "false()";
            result = !util.execXQuery(querry,collection).equals("false");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                util.closeCollection(collection);
            } catch (Exception e) {
                logger.error("IsAdminAccess, coolection exception: " + e.getMessage());
            }
            collection = old;
        }
        logger.info("isAdminAccess: " + result);
        return result;        
    }


    public boolean isDefaultUser(String user) {
        return (user.equals("SYSTEM") || user.equals("admin") ||user.equals("eXide") ||user.equals("guest") ||user.equals("monex") ||user.equals("nobody") ||user.equals("packageservice"));
    }
}
