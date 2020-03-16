package hu.sule.administration.service;

import hu.sule.administration.util.Util;
import hu.sule.administration.model.ExistDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;


@Service
public class ExistDbCredentialsService {

    @Autowired
    private Util util;

    private static final Logger logger = LoggerFactory.getLogger(ExistDbCredentialsService.class);

    private static ExistDetails details = new ExistDetails();

    public String initDatabaseDriver(String username, String password, String url) {
        logger.info("initializeDatabaseDriver");
        details.setUsername(username);
        details.setPassword(password);
        details.setIp(url);
        details.setUrl("xmldb:exist://" + url + "/exist/xmlrpc");
        try {
            util.initDatabaseDriver(details.getDriver());

        } catch (
                ClassNotFoundException e) {
            logger.error("DriverClassNotFoundException: " + e.getMessage());
        } catch (
                InstantiationException e) {
            logger.error("InstantiationException: " + e.getMessage());
        } catch (
                IllegalAccessException e) {
            logger.error("IllegalAccessException: " + e.getMessage());
        } catch (
                XMLDBException e) {
            logger.error("XMLDBException: " + e.getMessage());
        }
        return "OK";
    }

    public static ExistDetails getDetails() {
        return details;
    }

    public static String getDetailsPass() {
        return details.getPassword();
    }


}
