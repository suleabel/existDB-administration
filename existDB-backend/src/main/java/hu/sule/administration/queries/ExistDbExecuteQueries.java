package hu.sule.administration.queries;

import hu.sule.administration.model.ExistDetails;
import hu.sule.administration.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExistDbExecuteQueries {
    @Autowired
    private Util util;

        public String evalXqueryasString(ExistDetails details, String query) {
        String xquery = "xquery version '3.1';\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\" , \"" + details.getUsername() + "\", \"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        util:eval(\"" + query + "\")\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, xquery);
    }

    public String evalXqueryasPath(ExistDetails details, String url) {
        String xquery = "xquery version '3.1';\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\" , \"" + details.getUsername() + "\", \"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        util:eval(xs:anyURI(\"" + url + "\"))\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, xquery);
    }

}
