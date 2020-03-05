package com.example.demo.queries;

import com.example.demo.model.ExistDetails;
import com.example.demo.model.ExistFileManagerModel;
import com.example.demo.model.ForStoreResourceAndColl;
import com.example.demo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExistDbCollectionManagerQueries {

    @Autowired
    private Util util;

    public String getCollectionContent(ExistDetails details, String collection){
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"/db\",\"" + details.getUsername() + "\", \"" + details.getPassword() + "\" ,false())) then\n" +
                "xmldb:get-child-collections(\"" + collection + "\")\n" +
                "else\n" +
                "false()";
        System.out.println(query);
        return util.stringResultQuery(details, query);
    }
    public String getCollectionResources(ExistDetails details, String collection){
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"/db\",\"" + details.getUsername() + "\", \"" + details.getPassword() + "\" ,false())) then\n" +
                "xmldb:get-child-resources(\"" + collection + "\")\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String getResourceData(ExistDetails details, String baseCollection, String resource){
        String query = "xquery version \"3.1\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        let $rsource := xs:anyURI(\"" + baseCollection + "/" + resource + "\")\n" +
                "        let $permissions := sm:get-permissions($rsource)/sm:permission\n" +
                "        let $owner := $permissions/@owner/string()\n" +
                "        let $group := $permissions/@group/string()\n" +
                "        let $canWrite := sm:has-access($rsource, \"w\")\n" +
                "        let $mode :=  string($permissions/@mode)\n" +
                "        let $date := xmldb:last-modified(\"" + baseCollection + "/" + "\", \"" + resource + "\")\n" +
                "        return ($owner, $group, $canWrite, $mode, format-dateTime($date, \"[M00]/[D00]/[Y0000] [H00]:[m00]:[s00]\"))\n" +
                "    )\n" +
                "else\n" +
                "false()\n";
        return util.stringResultQuery(details, query);
    }

    public String saveResourceBin(ExistDetails details, ForStoreResourceAndColl storeResource){
        String query = "xquery version \"3.1\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        xmldb:store-as-binary(\"" + storeResource.getUrl() + "\",\"" + storeResource.getFileName() + "\", util:string-to-binary(\"" + storeResource.getContent().replaceAll("\"","'") + "\"))\n" +
                "    )\n" +
                "else\n" +
                "false()";
        System.out.println(query);
        return util.stringResultQuery(details, query);
    }

    public String saveResourceXml(ExistDetails details, ForStoreResourceAndColl storeResource){
        String query = "xquery version \"3.1\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        xmldb:store-as-binary(\"" + storeResource.getUrl() + "\",\"" + storeResource.getFileName() + "\", util:string-to-binary(\"" + storeResource.getContent().replaceAll("\"","'") + "\"))\n" +
                "    )\n" +
                "else\n" +
                "false()";
        System.out.println(query);
        return util.stringResultQuery(details, query);
    }

    public String createCollection(ExistDetails details, ForStoreResourceAndColl storeResource){
        String query = "xquery version \"3.1\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        xmldb:create-collection(\"" + storeResource.getUrl() + "\",\"" + storeResource.getFileName() + "\")\n" +
                "    )\n" +
                "else\n" +
                "false()";
        System.out.println(query);
        return util.stringResultQuery(details, query);
    }

    public String removeCollection(ExistDetails details, ExistFileManagerModel existFileManagerModel){
        String query = "xquery version \"3.1\";\n" +
                "if(xmldb:login(\"" + details.getCollection()+ "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        xmldb:remove(\"" + existFileManagerModel.getPath() + "/" + existFileManagerModel.getName() + "\")\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String readBinaryFile(ExistDetails details, String resUrl){
        String query = "xquery version \"3.1\";\n" +
                "import module namespace util=\"http://exist-db.org/xquery/util\" at \"java:org.exist.xquery.functions.util.UtilModule\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "       util:binary-to-string(util:binary-doc(\"" + resUrl + "\"))\n" +
                "    )\n" +
                "else\n" +
                "false()\n";
        return util.stringResultQuery(details, query);
    }

    public String readXmlFile(ExistDetails details, String confUrl){
        String query = "xquery version \"3.1\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\",false())) then\n" +
                "    (\n" +
                "       doc(\"" + confUrl + "\")\n" +
                "    )\n" +
                "    else\n" +
                "    false()\n";
        return util.stringResultQuery(details, query);
    }

    public String deleteResource(ExistDetails details, ExistFileManagerModel existFileManagerModel) {
        String query = "xquery version \"3.1\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        xmldb:remove(\"" + existFileManagerModel.getPath() + "\",\"" + existFileManagerModel.getName() + "\")\n" +
                "    )\n" +
                "else\n" +
                "false()";
        System.out.println(query);
        return "dummy success";
    }

    public String editResCred(ExistDetails details, ExistFileManagerModel existFileManagerModel) {
        String query = "";
        System.out.println(existFileManagerModel.toString());
        return "dummy success";
    }
}
