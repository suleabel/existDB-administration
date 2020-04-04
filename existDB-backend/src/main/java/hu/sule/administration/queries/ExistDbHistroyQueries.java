package hu.sule.administration.queries;

import hu.sule.administration.model.ExistDetails;
import hu.sule.administration.model.VersionByRevModel;
import hu.sule.administration.model.VersionsModel;
import hu.sule.administration.service.ExistDbCredentialsService;
import hu.sule.administration.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExistDbHistroyQueries {
    @Autowired
    private Util util;

    public String getResHistroy(ExistDetails details, String path){
        String query = "xquery version \"3.1\";\n" +
                "import module namespace v=\"http://exist-db.org/versioning\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        v:history(doc(\"" + path + "\"))\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String getDiffertencesByRev(ExistDetails details, VersionByRevModel data){
        String query = "xquery version \"3.1\";\n" +
                "import module namespace v=\"http://exist-db.org/versioning\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        v:diff(doc(\"" + data.getPath() + "\")," + data.getRevNo() + ")\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String restoreDocByRev(ExistDetails details, VersionByRevModel data){
        String query = "xquery version \"3.1\";\n" +
                "import module namespace v=\"http://exist-db.org/versioning\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        v:doc(doc(\"" + data.getPath() + "\")," + data.getRevNo() + ")\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }
}
