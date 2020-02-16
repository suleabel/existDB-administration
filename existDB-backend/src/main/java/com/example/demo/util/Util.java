package com.example.demo.util;

import com.example.demo.domain.ExistDetails;
import org.springframework.stereotype.Component;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;
import javax.xml.transform.OutputKeys;

@Component
public class Util {

    private Collection collection = null;

    public void initDatabaseDriver(String driver){
        try{
            Class aClass = Class.forName(driver);
            System.out.println("aClass.getName() = " + aClass.getName());
            Database database = (Database) aClass.newInstance();
            database.setProperty("create-database", "true");
            DatabaseManager.registerDatabase(database);
        }catch (ClassNotFoundException e){
            System.out.println("DriverClassNotFoundException: " + e.getMessage());
        }catch (InstantiationException e){
            System.out.println("InstantiationException: " + e.getMessage());
        }catch (IllegalAccessException e){
            System.out.println("IllegalAccessException: " + e.getMessage());
        }catch (XMLDBException e){
            System.out.println("XMLDBException: " + e.getMessage());
        }
    }

    public String stringResultQuery(ExistDetails details, String query){
        Collection old = collection;
        String result = null;
        try {
            closeCollection(collection);
            collection = DatabaseManager.getCollection(details.getUrl() + details.getCollection());
            result = execXQuery(query, collection);
        } catch (Exception e){
            System.out.println("Collection exception: " + e.getMessage());
        } finally {
            try {
                closeCollection(collection);
            }catch (Exception ee){
                System.out.println("Collection exception: " + ee.getMessage());
            }
            collection = old;
        }
        return result;
    }

    public boolean booleanResultQuery(ExistDetails details, String query) {
        Collection old = collection;
        boolean result = false;
        try {
            collection = DatabaseManager.getCollection(details.getUrl() + details.getCollection(), details.getUsername(), details.getPassword());
            System.out.println("asdasdasdasdasd" + execXQuery(query,collection));
            result = true;
        } catch (XMLDBException e) {
            System.out.println("User delete Exception: " + e.getMessage());
        } finally {
            try {
                closeCollection(collection);
            }catch (Exception ee){
                System.out.println("Collection exception: " + ee.getMessage());
            }
            collection = old;
        }
        return result;
    }

    public void closeCollection(Collection collection) throws Exception {
        if (collection != null)
            collection.close();
    }

    public String execXQuery(String query, Collection collection) {
        StringBuilder sb = new StringBuilder();
        try {
            XQueryService service = (XQueryService) collection.getService("XQueryService", "1.0");
            service.setProperty(OutputKeys.INDENT, "yes");
            service.setProperty(OutputKeys.ENCODING, "UTF-8");
            CompiledExpression compiled = service.compile(query);
            ResourceSet result = service.execute(compiled);//service.query(res,query);//since the queries will be simple, compilation should not bee needed
            for (int i = 0; i < (int) result.getSize(); i++) {
                XMLResource r = (XMLResource) result.getResource((long) i);
                sb.append(r.getContent().toString()).append("\n");
            }
        } catch (XMLDBException e) {
            System.out.println("execQuery Exception: " + e.getMessage());
        }
        return sb.toString().trim();
    }
}
