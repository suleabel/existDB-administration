package hu.sule.administration.queries;

import hu.sule.administration.model.ExistDetails;
import hu.sule.administration.model.ViewCreateModel;
import hu.sule.administration.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExistDbViewQueries {

    @Autowired
    private Util util;

    public String genViewTrigger(ViewCreateModel viewCreateModel, String condition){
        return "xquery version \"3.1\";\n" +
                "module namespace trigger='http://exist-db.org/xquery/trigger';\n" +
                "declare namespace xmldb='http://exist-db.org/xquery/xmldb';\n" +
                "\n" +
                "declare function trigger:after-update-document($uri as xs:anyURI) {\n" +
                "    if(" + condition + ") then (\n" +
                "        local:gen-view($uri)\n" +
                "        )\n" +
                "        else false()\n" +
                "};\n" +
                "\n" +
                "declare function local:gen-view($uri as xs:string) {\n" +
                "    (:fullpath for the view:)\n" +
                "    let $view_full_path := '" + viewCreateModel.getViewCollection() + "/" + viewCreateModel.getViewName() + "'\n" +
                "    (:path for view:)\n" +
                "    let $collection := '" + viewCreateModel.getViewCollection() + "'\n" +
                "    (:view doc name:)\n" +
                "    let $doc-name := '"+ viewCreateModel.getViewName() +"'\n" +
                "    (:query expression:)\n" +
                "    let $view_content := " + viewCreateModel.getQueryExpression() + "\n" +
                "    return\n" +
                "        (\n" +
                "        if (not(doc-available($view_full_path))) then\n" +
                "            xmldb:store($collection, $doc-name, <view>{$view_content}</view>)\n" +
                "        else (\n" +
                "            local:log-event($view_full_path),\n"+
                "            xmldb:remove($collection, $doc-name),\n" +
                "            xmldb:store($collection, $doc-name, <view>{$view_content}</view>)\n" +
                "            )\n" +
                "        )\n" +
                "};\n" +
                "declare function local:log-event($view_full_path as xs:string) {\n" +
                "    let $log-collection := \"/db\"\n" +
                "    let $log := \"view-triggers-log.xml\"\n" +
                "    let $log-uri := concat($log-collection, \"/\", $log)\n" +
                "    return\n" +
                "        (\n" +
                "        if (not(doc-available($log-uri))) then\n" +
                "            xmldb:store($log-collection, $log, <viewTriggers/>)\n" +
                "        else ()\n" +
                "        ,\n" +
                "        (: log the trigger details to the log file :)\n" +
                "        update insert <viewTrigger viewTrigger='{$view_full_path}' timestamp=\"{current-dateTime()}\"/> into doc($log-uri)/viewTriggers\n" +
                "        )\n" +
                "};";
    }

    public boolean logViewCreation(ExistDetails details, ViewCreateModel viewCreateModel, String configLocation, String queryName, String user, String time){
        String query = "xquery version \"3.1\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        let $view-log-collection := '/db'\n" +
                "        let $view-log := 'createdViews.xml'\n" +
                "        let $view-log-uri := concat($view-log-collection, '/', $view-log)\n" +
                "        return \n" +
                "            (\n" +
                "                if (not(doc-available($view-log-uri))) then\n" +
                "                    xmldb:store($view-log-collection, $view-log, <views/>)\n" +
                "                else (),\n" +
                "            update insert <view user='" + user + "' date='" + time + "' config-location='" + configLocation + "' query-name='" + queryName + "' view-location='" + viewCreateModel.getViewCollection() + "/" + viewCreateModel.getViewName() + "'/> into doc($view-log-uri)/views,\n" +
                "            true()\n" +
                "            )\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.booleanResultQuery(details, query);
    }


}
