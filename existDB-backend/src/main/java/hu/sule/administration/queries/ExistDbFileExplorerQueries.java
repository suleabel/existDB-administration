package hu.sule.administration.queries;

import hu.sule.administration.model.ExistDetails;
import hu.sule.administration.model.SerializeFile;
import hu.sule.administration.model.StoreDirOrFileModel;
import hu.sule.administration.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xmldb.api.base.XMLDBException;

@Component
public class ExistDbFileExplorerQueries {

    @Autowired
    private Util util;

    public String getDirectoryContent(ExistDetails details, String dirname) throws XMLDBException {
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "declare variable $exist-home := system:get-exist-home();\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\" , \"" + details.getUsername() + "\", \"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        file:list(\"" + dirname + "\")\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String getRootDirectory(ExistDetails details) throws XMLDBException {
        String query = "xquery version \"3.1\";\n" +
                "declare variable $exist-home := system:get-exist-home();\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\" , \"" + details.getUsername() + "\", \"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        $exist-home\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String readFileContent(ExistDetails details, String url) throws XMLDBException {
        String query = "xquery version \"3.1\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\" , \"" + details.getUsername() + "\", \"" + details.getPassword() + "\")) then\n" +
                "   (\n" +
                "        <result>\n" +
                "            {\n" +
                "                file:read(\"" + url + "\")  \n" +
                "            }\n" +
                "        </result>\n" +
                "    )" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String createFile(ExistDetails details, SerializeFile file){
        String query = "xquery version \"3.1\";\n" +
                "declare variable $data := xs:string(\"" + file.getContent() + "\");\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "       file:serialize-binary(util:string-to-binary($data),\"" + file.getPath() + "/" + file.getName() + "\")\n" +
                "    )\n" +
                "else\n" +
                "false()";
        System.out.println(query);
        return util.stringResultQuery(details, query);
    }

    public String createXmlFile(ExistDetails details, SerializeFile file){
        String query = "xquery version \"3.1\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        file:serialize(" + file.getContent() + ",\"" + file.getPath() + "/" + file.getName() + "\",\"" + file.getParameters() + "\")\n" +
                "    )\n" +
                "else\n" +
                "false()";
        System.out.println(query);
        return util.stringResultQuery(details, query);
    }

    public String mkDir(ExistDetails details, StoreDirOrFileModel storeDirOrFileModel) {
        String query = "xquery version \"3.1\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        file:mkdir(\"" + storeDirOrFileModel.getUrl() + "/" + storeDirOrFileModel.getName()+ "\")\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String delete(ExistDetails details, StoreDirOrFileModel storeDirOrFileModel) {
        String query = "xquery version \"3.1\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        file:delete(\"" + storeDirOrFileModel.getUrl() + "/" + storeDirOrFileModel.getName()+ "\")\n" +
                "    )\n" +
                "else\n" +
                "false()";
        System.out.println(query);
        return util.stringResultQuery(details, query);
    }

    // TODO meg kell nézni szükséges-e a file:delete
    public String editXmlFile(ExistDetails details, SerializeFile file) {
        String query = "xquery version \"3.1\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        file:delete(\"" + file.getPath() + "/" + file.getName() + "\"),\n" +
                "        file:serialize(" + file.getContent() + ",\"" + file.getPath() + "/" + file.getName() + "\",\"\")\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    // TODO meg kell nézni szükséges-e a file:delete
    public String editFile(ExistDetails details, SerializeFile file){
        String query = "xquery version \"3.1\";\n" +
                "declare variable $data := xs:string(\"" + file.getContent() + "\");\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        file:delete(\"" + file.getPath() + "/" + file.getName() + "\"),\n" +
                "        file:serialize-binary(util:string-to-binary($data),\"" + file.getPath() + "/" + file.getName() + "\")\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }
}
