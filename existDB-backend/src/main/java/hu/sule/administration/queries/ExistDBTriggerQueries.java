package hu.sule.administration.queries;

import hu.sule.administration.model.EditTriggerModel;
import hu.sule.administration.model.ExistDetails;
import hu.sule.administration.model.TriggerModel;
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

    public String saveEditedTrigger(ExistDetails details, String url, String conf){
        String query = "xquery version \"3.1\";\n" +
                "declare variable $collection := \"" + url + "\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        xmldb:remove($collection,\"collection.xconf\"),\n" +
                "        let $result := xmldb:store($collection,\"collection.xconf\",\"" + conf + "\")\n" +
                "        let $reindex :=\n" +
                "                    if (starts-with($collection, \"/db/system/config\")) then\n" +
                "                        substring-after($collection, \"/db/system/config\")\n" +
                "                    else\n" +
                "                        $collection\n" +
                "        return(           \n" +
                "            if(xmldb:reindex($reindex) and exists($result)) then \n" +
                "                true()\n" +
                "            else \n" +
                "                false()\n" +
                "            )\n" +
                "        )\n" +
                "else\n" +
                "false()";
        System.out.println(query);
        String result = util.stringResultQuery(details, query);
        System.out.println("result: " + result);
        return result;
    }
}
