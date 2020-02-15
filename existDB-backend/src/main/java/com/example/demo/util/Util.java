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
