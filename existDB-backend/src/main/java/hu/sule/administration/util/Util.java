package hu.sule.administration.util;

import hu.sule.administration.exceptions.ApiException;
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
        Database database = (Database) aClass.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);
    }

    public String stringResultQuery(ExistDetails details, String query) {
        Collection old = collection;
        String result = "";
        try {
            closeCollection(collection);
            collection = DatabaseManager.getCollection(details.getUrl() + details.getCollection());
            result = execXQuery(query, collection);
        } catch (Exception e) {
            if(e.getMessage().equals("")){
                throw new ApiException("no error message","stringResultQuery","XMLDBException", e.getStackTrace());
            } else {
                throw new ApiException(e.getMessage(),"stringResultQuery","XMLDBException", e.getStackTrace());
            }
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
                throw new ApiException("no error message","booleanResultQuery","XMLDBException", e.getStackTrace());
            }
            throw new ApiException(e.getMessage(),"booleanResultQuery","XMLDBException", e.getStackTrace());
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
            service.setProperty(OutputKeys.ENCODING, "UTF-8");
            CompiledExpression compiled = service.compile(query);
            ResourceSet result = service.execute(compiled);
            for (int i = 0; i < (int) result.getSize(); i++) {
                XMLResource r = (XMLResource) result.getResource((long) i);
                sb.append(r.getContent().toString()).append("\n");
            }
        } catch (XMLDBException e) {
            if(e.getMessage().equals("")){
                throw new ApiException("no error message","execXQuery","XMLDBException", e.getStackTrace());
            } else {
                System.out.println("failed method: \n" + query);
                throw new ApiException(e.getMessage(),"execXQuery","XMLDBException", e.getStackTrace());
            }
        }
        return sb.toString().trim();
    }
}
