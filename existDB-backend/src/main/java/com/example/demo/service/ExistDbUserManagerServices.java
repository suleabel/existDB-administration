package com.example.demo.service;

import com.example.demo.model.ExistDetails;
import com.example.demo.util.Util;
import org.springframework.stereotype.Component;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;

@Component
public class ExistDbUserManagerServices {

    private Collection collection = null;
    private static Util util = new Util();

    /*
    public boolean createUser(ExistDetails details, String user, String pass, String group) throws Exception {
        Collection old = collection;//save previos context
        util.closeCollection(collection);
        boolean result = false;
        try {
            collection = DatabaseManager.getCollection(details.getUrl() + DB);
            if (userExists(user))//first: is user already existing?
                return true;
            String query = "xquery version \"3.1\";\n" +
                    "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                    "\n" +
                    "if(xmldb:login(\"/db\",\"" + details.getUsername() + "\", \"" + details.getPassword() + "\" ,false())) then\n" +
                    "    sm:create-account(\"" + user + "\", \"" + pass + "\", \"" + group + "\", \"" + "" + "\")\n" +
                    "else\n" +
                    "\tfalse()";
            result = !util.execXQuery(query,collection).equals("false");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            util.closeCollection(collection);//'logout'
            collection = old;
        }
        return result;
    }

    public boolean deleteUser(ExistDetails details, String deletedUser) throws Exception {
        Collection old = collection;
        util.closeCollection(collection);
        boolean userIsDeleted = false;
        try {
            collection = DatabaseManager.getCollection(details.getUrl() + DB);
            String query = "xquery version \"3.1\";\n" +
                    "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                    "if(xmldb:login(\"/db\",\"" + details.getUsername() + "\", \"" + details.getPassword() + "\" ,false())) then\n" +
                    "    sm:remove-account(\"" + deletedUser + "\")\n" +
                    "else\n" +
                    "\tfalse()";
            System.out.println(util.execXQuery(query,collection));
            userIsDeleted = true;
        } catch (XMLDBException e) {
            System.out.println("User delete Exception: " + e.getMessage());
        } finally {
            util.closeCollection(collection);
            collection = old;
        }

        return userIsDeleted;

    }
*/
    public String listUsers(ExistDetails details){
            String query = "xquery version \"3.1\";\n" +
                    "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                    "if(xmldb:login(\"/db\",\"" + details.getUsername() + "\", \"" + details.getPassword() + "\" ,false())) then\n" +
                    "    sm:list-users()\n" +
                    "else\n" +
                    "\tfalse()";
        return util.stringResultQuery(details,query);
    }

    public boolean deleteUser(ExistDetails details, String username){
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
        }
        return !userExists(username);
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


}
