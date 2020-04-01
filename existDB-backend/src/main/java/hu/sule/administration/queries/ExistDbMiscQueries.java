package hu.sule.administration.queries;

import hu.sule.administration.model.ExistDetails;
import hu.sule.administration.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExistDbMiscQueries {

    @Autowired
    private Util util;

    public String getDbVersion(ExistDetails details){
        String query = "xquery version \"3.1\";\n" +
                "if(xmldb:login(\"/db/\",\"admin\",\"\")) then\n" +
                "    (\n" +
                "        system:get-version()\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

}
