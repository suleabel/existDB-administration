package hu.sule.administration.util;

import hu.sule.administration.model.ExistDBUser;
import hu.sule.administration.model.ExistDBUsers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.ArrayList;

@Component
public class JaxbUnmarshaller {

    private static final Logger logger = LoggerFactory.getLogger(JaxbUnmarshaller.class);

    public ArrayList<ExistDBUser> unmarshallUsersAndDetails(String data) {
        ArrayList<ExistDBUser> existDBUserss = new ArrayList<>();
        JAXBContext jaxbContext;
        try{
            jaxbContext = JAXBContext.newInstance(ExistDBUsers.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ExistDBUsers existDBUsers = (ExistDBUsers) jaxbUnmarshaller.unmarshal(new StringReader(data));
            existDBUserss.addAll(existDBUsers.getExistDBUserList());
        } catch(JAXBException jaxbe) {
            logger.error("JAXBException: " + jaxbe);
        }
        return existDBUserss;
    }
}
