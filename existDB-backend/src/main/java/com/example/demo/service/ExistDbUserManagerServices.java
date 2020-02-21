package com.example.demo.service;

import com.example.demo.model.ExistDBUserForCreate;
import com.example.demo.model.ExistDetails;
import com.example.demo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class ExistDbUserManagerServices {

    private Collection collection = null;

    private static Util util = new Util();

    public String createUser(ExistDetails details, ExistDBUserForCreate user){
        System.out.println(user.toString());
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        sm:create-account(\"" + user.getUsername() + "\", \"" + user.getPassword() + "\", \"" + user.getPrimaryGroup() + "\", (\""+ user.getGroupsAsString() +"\"), \"" + user.getFullName() + "\", \"" + user.getDesc() + "\"),\n" +
                "        true()\n" +
                "    )\n" +
                "else\n" +
                "false()";
        if(userExists(user.getUsername())){
            return "User is exist";

        }
        util.stringResultQuery(details, query);
        return "User created";
    }


    public String enableDisableAccount(){
        String query = "sm:set-account-enabled($username as xs:string, $enabled as xs:boolean)";
        return "";
    }

    public String listUsers(ExistDetails details){
            String query = "xquery version \"3.1\";\n" +
                    "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                    "if(xmldb:login(\"/db\",\"" + details.getUsername() + "\", \"" + details.getPassword() + "\" ,false())) then\n" +
                    "    sm:list-users()\n" +
                    "else\n" +
                    "\tfalse()";
        return util.stringResultQuery(details,query);
    }

    public String deleteUser(ExistDetails details, String username){
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "("+
                "sm:remove-account(\"" + username + "\"),\n"+
                "true()\n" +
                ")" +
                "else\n" +
                "false()";
        if(userExists(username)){
            util.stringResultQuery(details, query);
            return "User is deleted";
        }
        return "User is not exist";
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

    private boolean userExists(String user) {
        boolean result = false;
        try {
            String query = "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\"; \n" +
                    "sm:user-exists(\"" + user + "\")";
            result = util.execXQuery(query,collection).equals("true");

        } catch (Exception ignored) {

        }
        return result;
    }

    public boolean isAndminAccess(ExistDetails details){
        Collection old = collection;
        boolean result = false;
        try{
            util.closeCollection(collection);
            System.out.println("isAdminAccess: " + details.toString());
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
                System.out.println("IsAdminAccess, coolection exception: " + e.getMessage());
            }
            collection = old;
        }
        return result;        
    }


    public boolean isDeaultUser(String user) {
        if(user.equals("SYSTEM") || user.equals("admin") ||user.equals("eXide") ||user.equals("guest") ||user.equals("monex") ||user.equals("nobody") ||user.equals("packageservice")){
            return true;
        }
        return false;
    }

    public boolean isDefaultGroup(String group) {
        if(group.equals("dba") || group.equals("eXide") || group.equals("guest") || group.equals("monex") || group.equals("nogroup") || group.equals("packageservice")) {
            return true;
        }else{
            return false;
        }
    }
}
