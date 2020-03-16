package hu.sule.administration.queries;

import hu.sule.administration.model.ExistCollectionManagerModel;
import hu.sule.administration.model.ExistDetails;
import hu.sule.administration.model.ForStoreResourceAndColl;
import hu.sule.administration.util.Util;
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

    public String getCollectionContent2(ExistDetails details, String url){
        String query = "xquery version \"3.1\";\n" +
                "declare variable $url := \"" + url + "\";\n" +
                "declare variable $collections := xmldb:get-child-collections($url);\n" +
                "declare variable $resources := xmldb:get-child-resources($url);\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        <result>\n" +
                "            {for $collection in $collections\n" +
                "                let $fullPath := string-join(($url, '/' , $collection),\"\")\n" +
                "                let $permissions := sm:get-permissions($fullPath)/sm:permission\n" +
                "                let $owner := $permissions/@owner/string()\n" +
                "                let $group := $permissions/@group/string()\n" +
                "                let $canWrite := sm:has-access($fullPath, \"w\")\n" +
                "                let $mode :=  string($permissions/@mode)\n" +
                "               let $date := xmldb:last-modified($url, $collection)\n" +
                "                return (\n" +
                "                    <exist:collection name=\"{$collection}\" path=\"{$url}\" owner=\"{$owner}\" group=\"{$group}\" writable=\"{$canWrite}\" mode=\"{$mode}\" date=\"{format-dateTime($date, \"[M00]/[D00]/[Y0000] [H00]:[m00]:[s00]\")}\" resource=\"false\">\n" +
                "                    </exist:collection>\n" +
                "                )\n" +
                "            }   \n" +
                "            {for $resource in $resources\n" +
                "                let $fullPath := string-join(($url, '/' , $resource),\"\")\n" +
                "                let $permissions := sm:get-permissions($fullPath)/sm:permission\n" +
                "                let $owner := $permissions/@owner/string()\n" +
                "                let $group := $permissions/@group/string()\n" +
                "                let $canWrite := sm:has-access($fullPath, \"w\")\n" +
                "                let $mode :=  string($permissions/@mode)\n" +
                "                let $date := xmldb:last-modified($url, $resource)\n" +
                "                return (\n" +
                "                    <exist:resource name=\"{$resource}\" path=\"{$url}\" owner=\"{$owner}\" group=\"{$group}\" writable=\"{$canWrite}\" mode=\"{$mode}\" date=\"{format-dateTime($date, \"[M00]/[D00]/[Y0000] [H00]:[m00]:[s00]\")}\" resource=\"true\">\n" +
                "                    </exist:resource>\n" +
                "                )\n" +
                "            }\n" +
                "        </result>\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String saveEditedRes(ExistDetails details, ForStoreResourceAndColl forStoreResourceAndColl){
        String query = "xquery version \"3.1\";\n" +
                "declare variable $isBinary := xs:boolean(\"" + forStoreResourceAndColl.isBinary() + "\");\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        xmldb:remove(\"" + forStoreResourceAndColl.getUrl() + "\",\"" + forStoreResourceAndColl.getFileName() + "\"),\n" +
                "        if($isBinary) then\n" +
                "            (\n" +
                "                xmldb:store-as-binary(\"" + forStoreResourceAndColl.getUrl() + "\",\"" + forStoreResourceAndColl.getFileName() + "\",util:string-to-binary(\"" + forStoreResourceAndColl.getContent().replaceAll("\"", "'") + "\"))\n" +
                "            )\n" +
                "            else\n" +
                "            (\n" +
                "                xmldb:store(\"" + forStoreResourceAndColl.getUrl() + "\",\"" + forStoreResourceAndColl.getFileName() + "\",\"" + forStoreResourceAndColl.getContent().replaceAll("\"","'") + "\")\n" +
                "            )\n" +
                "    )\n" +
                "else\n" +
                "false()";
        //System.out.println(query);
        return util.stringResultQuery(details, query);
    }

    public String saveResource(ExistDetails details, ForStoreResourceAndColl forStoreResourceAndColl){
        String query = "xquery version \"3.1\";\n" +
                "declare variable $isBinary := xs:boolean(\"" + forStoreResourceAndColl.isBinary() + "\");\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        if($isBinary) then\n" +
                "            (\n" +
                "                xmldb:store-as-binary(\"" + forStoreResourceAndColl.getUrl() + "\",\"" + forStoreResourceAndColl.getFileName() + "\",util:string-to-binary(\"" + forStoreResourceAndColl.getContent().replaceAll("\"", "'") + "\"))\n" +
                "            )\n" +
                "            else\n" +
                "            (\n" +
                "                xmldb:store(\"" + forStoreResourceAndColl.getUrl() + "\",\"" + forStoreResourceAndColl.getFileName() + "\",\"" + forStoreResourceAndColl.getContent().replaceAll("\"", "'") + "\")\n" +
                "            )\n" +
                "    )\n" +
                "else\n" +
                "false()";
        System.out.println(query);
        return "not working this function!!";
        //return util.stringResultQuery(details, query);
    }

    public String createCollection(ExistDetails details, ForStoreResourceAndColl storeResource){
        String query = "xquery version \"3.1\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        xmldb:create-collection(\"" + storeResource.getUrl() + "\",\"" + storeResource.getFileName() + "\")\n" +
                "    )\n" +
                "else\n" +
                "false()";
        //System.out.println(query);
        return util.stringResultQuery(details, query);
    }

    public String removeCollection(ExistDetails details, ExistCollectionManagerModel existCollectionManagerModel){
        String query = "xquery version \"3.1\";\n" +
                "if(xmldb:login(\"" + details.getCollection()+ "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        xmldb:remove(\"" + existCollectionManagerModel.getPath() + "/" + existCollectionManagerModel.getName() + "\")\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

//    public String readBinaryFile(ExistDetails details, String resUrl){
//        String query = "xquery version \"3.1\";\n" +
//                "import module namespace util=\"http://exist-db.org/xquery/util\" at \"java:org.exist.xquery.functions.util.UtilModule\";\n" +
//                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
//                "    (\n" +
//                "       util:binary-to-string(util:binary-doc(\"" + resUrl + "\"))\n" +
//                "    )\n" +
//                "else\n" +
//                "false()\n";
//        return util.stringResultQuery(details, query);
//    }
//
//    public String readXmlFile(ExistDetails details, String confUrl){
//        String query = "xquery version \"3.1\";\n" +
//                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\",false())) then\n" +
//                "    (\n" +
//                "       doc(\"" + confUrl + "\")\n" +
//                "    )\n" +
//                "    else\n" +
//                "    false()\n";
//        return util.stringResultQuery(details, query);
//    }

    public String readFile(ExistDetails details, String resUrl) {
        String query = "xquery version \"3.1\";\n" +
                "declare variable $file := xs:string(\"" + resUrl + "\");\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        if(util:is-binary-doc($file)) then\n" +
                "            (\n" +
                "                 util:binary-to-string(util:binary-doc($file))\n" +
                "            )\n" +
                "            else\n" +
                "            (\n" +
                "                doc($file)\n" +
                "            )\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String deleteResource(ExistDetails details, ExistCollectionManagerModel existCollectionManagerModel) {
        String query = "xquery version \"3.1\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        xmldb:remove(\"" + existCollectionManagerModel.getPath() + "\",\"" + existCollectionManagerModel.getName() + "\")\n" +
                "    )\n" +
                "else\n" +
                "false()";
        //System.out.println(query);
        return util.stringResultQuery(details, query);
    }

    public String editResCred(ExistDetails details, ExistCollectionManagerModel existCollectionManagerModel) {
        String query = "";
        System.out.println(existCollectionManagerModel.toString());
        return "dummy success";
    }

    public boolean isBinary(ExistDetails details, String url) {
        String query = "xquery version \"3.1\";\n" +
                "declare variable $file := xs:string(\"" + url + "\");\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        if(util:is-binary-doc($file)) then\n" +
                "            (\n" +
                "                 true()\n" +
                "                     \n" +
                "            )\n" +
                "            else\n" +
                "            (\n" +
                "                false()\n" +
                "            )\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.booleanResultQuery(details, query);
    }
}
