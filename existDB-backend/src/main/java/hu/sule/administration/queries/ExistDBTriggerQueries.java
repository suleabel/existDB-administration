package hu.sule.administration.queries;

import hu.sule.administration.model.ExistDetails;
import hu.sule.administration.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ExistDBTriggerQueries {

    private static Util util = new Util();
    private static final Logger logger = LoggerFactory.getLogger(ExistDBTriggerQueries.class);

    public String initTriggerConfig(ExistDetails details, String path){
        String query = "xquery version \"3.1\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        xmldb:store(\"/db/system/config" + path + "\",\"collection.xconf\",\n" +
                "        <collection xmlns=\"http://exist-db.org/collection-config/1.0\">\n" +
                "\t    <triggers>\t\t\t\n" +
                "\t        <trigger class=\"org.exist.extensions.exquery.restxq.impl.RestXqTrigger\"/>\n" +
                "\t    </triggers>\n" +
                "        </collection>)\n" +
                "    )\n" +
                "else\n" +
                "false()";
        System.out.println(query);
        return util.stringResultQuery(details, query);
    }

    public boolean triggerConfigIsAvailable(ExistDetails details, String path){
        String query = "xquery version \"3.1\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        xmldb:collection-available(\"" + path + "\")\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query).contains("true");
    }
}
