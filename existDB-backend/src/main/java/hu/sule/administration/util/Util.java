package hu.sule.administration.util;

import hu.sule.administration.exceptions.CustomeException;
import hu.sule.administration.model.ExistDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;

import javax.xml.transform.OutputKeys;

@Component
public class Util {

    private Collection collection = null;

    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    public void initDatabaseDriver(String driver) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
        Class aClass = Class.forName(driver);
        System.out.println("aClass.getGroupName() = " + aClass.getName());
        Database database = (Database) aClass.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);
    }

    public String stringResultQuery(ExistDetails details, String query) {
        Collection old = collection;
        String result = null;
        try {
            closeCollection(collection);
            collection = DatabaseManager.getCollection(details.getUrl() + details.getCollection());
            result = execXQuery(query, collection);
        } catch (Exception e) {
            if(e.getMessage().equals("")){
                throw new CustomeException("no error message","stringResultQuery","XMLDBException");
            }
            System.out.println(e.getMessage() + " stringResultQuery " + " XMLDBException");
            throw new CustomeException(e.getMessage(),"stringResultQuery","XMLDBException");
        } finally {
            try {
                closeCollection(collection);
            } catch (Exception ee) {
                logger.error("Close collection exception: " + ee.getMessage());
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
            result = !execXQuery(query, collection).equals("false");
        } catch (XMLDBException e) {
            if(e.getMessage().equals("")){
                throw new CustomeException("no error message","booleanResultQuery","XMLDBException");
            }
            System.out.println(e.getMessage() + " booleanResultQuery " + " XMLDBException");
            throw new CustomeException(e.getMessage(),"booleanResultQuery","XMLDBException");
        } finally {
            try {
                closeCollection(collection);
            } catch (Exception ee) {
                System.out.println("Collection exception: " + ee.getMessage());
            }
            collection = old;
        }
        return result;
    }

    public void closeCollection(Collection collection) throws XMLDBException {
        if (collection != null)
            collection.close();
    }

    public String execXQuery(String query, Collection collection) {
        StringBuilder sb = new StringBuilder();
        try {
            XQueryService service = (XQueryService) collection.getService("XQueryService", "1.0");
            service.setProperty(OutputKeys.INDENT, "yes");
            service.setProperty(OutputKeys.ENCODING, "UTF-16");
            CompiledExpression compiled = service.compile(query);
            ResourceSet result = service.execute(compiled);
            for (int i = 0; i < (int) result.getSize(); i++) {
                XMLResource r = (XMLResource) result.getResource((long) i);
                sb.append(r.getContent().toString()).append("\n");
            }
        } catch (XMLDBException e) {
            if(e.getMessage().equals("")){
                throw new CustomeException("no error message","execXQuery","XMLDBException");
            }
            System.out.println("query: "+ query + ", " + e.getMessage() + " execXQuery " + " XMLDBException");
            throw new CustomeException(e.getMessage(),"execXQuery","XMLDBException");
        }
        return sb.toString().trim();
    }

//    public String execXQuery(String query, Collection collection) throws XMLDBException {
//        StringBuilder sb = new StringBuilder();
//        XQueryService service = (XQueryService) collection.getService("XQueryService", "1.0");
//        service.setProperty(OutputKeys.INDENT, "yes");
//        service.setProperty(OutputKeys.ENCODING, "UTF-16");
//        CompiledExpression compiled = service.compile(query);
//        ResourceSet result = service.execute(compiled);
//        for (int i = 0; i < (int) result.getSize(); i++) {
//            XMLResource r = (XMLResource) result.getResource((long) i);
//            sb.append(r.getContent().toString()).append("\n");
//        }
//        return sb.toString().trim();
//    }
//
//    public String stringResultQuery(ExistDetails details, String query) throws XMLDBException {
//        Collection old = collection;
//        closeCollection(collection);
//        collection = DatabaseManager.getCollection(details.getUrl() + details.getCollection());
//        String result = execXQuery(query, collection);
//        closeCollection(collection);
//        collection = old;
//        return result;
//    }
//
//    public boolean booleanResultQuery(ExistDetails details, String query) throws XMLDBException{
//        Collection old = collection;
//        boolean result = false;
//        collection = DatabaseManager.getCollection(details.getUrl() + details.getCollection(), details.getUsername(), details.getPassword());
//        result = !execXQuery(query, collection).equals("false");
//        closeCollection(collection);
//        collection = old;
//        return result;
//    }
}
