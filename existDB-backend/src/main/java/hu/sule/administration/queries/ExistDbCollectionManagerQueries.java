package hu.sule.administration.queries;

import hu.sule.administration.model.ExistCollectionManagerModel;
import hu.sule.administration.model.ExistDetails;
import hu.sule.administration.model.ForStoreResourceAndColl;
import hu.sule.administration.util.Util;
import org.exquery.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Component
public class ExistDbCollectionManagerQueries {

    @Autowired
    private Util util;

    public String getCollectionContent(ExistDetails details, String collection) throws XMLDBException {
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"/db\",\"" + details.getUsername() + "\", \"" + details.getPassword() + "\" ,false())) then\n" +
                "xmldb:get-child-collections(\"" + collection + "\")\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String getCollectionContent2(ExistDetails details, String url) throws XMLDBException {
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
                "                let $date := xmldb:last-modified($url, $collection)\n" +
                "                let $mime := xmldb:get-mime-type($fullPath)\n" +
                "                return (\n" +
                "                    <exist:collection name=\"{$collection}\" path=\"{$url}\" owner=\"{$owner}\" group=\"{$group}\" writeable=\"{$canWrite}\" mode=\"{$mode}\" mime=\"{$mime}\" date=\"{format-dateTime($date, \"[M00]/[D00]/[Y0000] [H00]:[m00]:[s00]\")}\" resource=\"false\">\n" +
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
                "                let $locked := xmldb:document-has-lock($url, $resource)\n" +
                "                let $date := xmldb:last-modified($url, $resource)\n" +
                "                let $mime := xmldb:get-mime-type($fullPath)\n" +
                "                return (\n" +
                "                    <exist:resource name=\"{$resource}\" path=\"{$url}\" owner=\"{$owner}\" group=\"{$group}\" writeable=\"{$canWrite}\" mode=\"{$mode}\" mime=\"{$mime}\" date=\"{format-dateTime($date, \"[M00]/[D00]/[Y0000] [H00]:[m00]:[s00]\")}\" locked=\"{$locked}\" resource=\"true\">\n" +
                "                    </exist:resource>\n" +
                "                )\n" +
                "            }\n" +
                "        </result>\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String saveEditedRes(ExistDetails details, ForStoreResourceAndColl forStoreResourceAndColl) throws XMLDBException {
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
        return util.stringResultQuery(details, query);
    }

    public String saveResource(ExistDetails details, ForStoreResourceAndColl forStoreResourceAndColl) throws XMLDBException {
        // System.out.println("create file: " + forStoreResourceAndColl);
        String query = "xquery version \"3.1\";\n" +
                "declare variable $collection := \"" + forStoreResourceAndColl.getUrl() + "\";\n" +
                "declare variable $resource := \"" + forStoreResourceAndColl.getFileName() + "\";\n" +
                "declare variable $data :=\"" + forStoreResourceAndColl.getContent().replaceAll("\"","'") + "\";\n" +
                "declare variable $isBinary := " + forStoreResourceAndColl.isBinary() + "();\n" +
                "declare variable $mime := \"" + forStoreResourceAndColl.getMime() + "\";\n" +
                "declare variable $path := string-join(($collection, \"/\", $resource),\"\");\n" +
                "if(xmldb:login(\"/db/\" , \"admin\", \"\")) then\n" +
                "    (\n" +
                "        if($isBinary) then\n" +
                "            xmldb:store-as-binary($collection, $resource, $data)\n" +
                "        else \n" +
                "            (\n" +
                "            if (string-length($mime) ne 0) then\n" +
                "                xmldb:store($collection, $resource, $data, $mime)\n" +
                "            else\n" +
                "                xmldb:store($collection, $resource, $data)\n" +
                "            )\n" +
                "    )\n" +
                "else\n" +
                "false()";
        // System.out.println(query);
        //return "not working this function!!";
        return util.stringResultQuery(details, query);
    }

    public String createCollection(ExistDetails details, ForStoreResourceAndColl storeResource) throws XMLDBException {
        String query = "xquery version \"3.1\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        xmldb:create-collection(\"" + storeResource.getUrl() + "\",\"" + storeResource.getFileName() + "\")\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String removeCollection(ExistDetails details, ExistCollectionManagerModel existCollectionManagerModel) throws XMLDBException {
        String query = "xquery version \"3.1\";\n" +
                "if(xmldb:login(\"" + details.getCollection()+ "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        xmldb:remove(\"" + existCollectionManagerModel.getPath() + "/" + existCollectionManagerModel.getName() + "\")\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }
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

    public String deleteResource(ExistDetails details, ExistCollectionManagerModel existCollectionManagerModel) throws XMLDBException {
        String query = "xquery version \"3.1\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        xmldb:remove(\"" + existCollectionManagerModel.getPath() + "\",\"" + existCollectionManagerModel.getName() + "\")\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String editResCred(ExistDetails details, ExistCollectionManagerModel existCollectionManagerModel) throws XMLDBException {
        String query = "xquery version \"3.1\";\n" +
                "declare variable $collection := \"" + existCollectionManagerModel.getPath() + "\";\n" +
                "declare variable $resource := \"" + existCollectionManagerModel.getName() + "\";\n" +
                "declare variable $path := string-join(($collection, \"/\", $resource),\"\");\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\" , \"" + details.getUsername() + "\", \"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "       sm:chgrp($path,\"" + existCollectionManagerModel.getGroup() + "\"),\n" +
                "       sm:chown($path,\"" + existCollectionManagerModel.getOwner() + "\"),\n" +
                "       sm:chmod($path,\"" + existCollectionManagerModel.getMode() + "\"),\n" +
                "       true()\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public boolean isBinary(ExistDetails details, String url) throws XMLDBException {
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

    public String evalXqueryasString(ExistDetails details, String query) throws XMLDBException{
        String xquery = "xquery version '3.1';\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\" , \"" + details.getUsername() + "\", \"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        util:eval(\"" + query + "\")\n" +
                "    )\n" +
                "else\n" +
                "false()";
        // System.out.println(xquery);
        return util.stringResultQuery(details, xquery);
    }

    public String evalXqueryasPath(ExistDetails details, String url) throws XMLDBException {
        String xquery = "xquery version '3.1';\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\" , \"" + details.getUsername() + "\", \"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        util:eval(xs:anyURI(\"" + url + "\"))\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, xquery);
    }

    public String unlockResource(ExistDetails details, String url) throws XMLDBException {
        String path = url.substring(0,url.lastIndexOf("/"));
        String resource = url.substring(url.lastIndexOf("/")+1);

        String xquery = "xquery version '3.1';\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\" , \"" + details.getUsername() + "\", \"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        xmldb:clear-lock(\"" + path + "\",\""+ resource +"\")\n" +
                "    )\n" +
                "else\n" +
                "false()";
        //System.out.println(xquery);
        return util.stringResultQuery(details,xquery);
    }
}
