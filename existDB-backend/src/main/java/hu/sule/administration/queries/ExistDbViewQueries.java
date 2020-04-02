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
                "            xmldb:remove($collection, $doc-name),\n" +
                "            xmldb:store($collection, $doc-name, <view>{$view_content}</view>)\n" +
                "            )\n" +
                "        )\n" +
                "};";
    }

//    public String storeViewTrigger(ExistDetails details, String triggerString, String trigger_name){
//        String query = "xquery version \"3.1\";\n" +
//                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
//                "    (\n" +
//                "        if(xmldb:collection-available(\"/db/view_triggers\"))then\n" +
//                "            xmldb:store(\"/db/view_triggers\", \"" + trigger_name + "\", " + triggerString.replace("\"", "'") + ", \"application/xquery\")\n" +
//                "        else(\n" +
//                "            xmldb:create-collection(\"/db\", \"view_triggers\"),\n" +
//                "            xmldb:store(\"/db/view_triggers\", \"" + trigger_name + "\", " + triggerString.replace("\"", "'") + ", \"application/xquery\")\n" +
//                "        ),\n" +
//                "        sm:chmod(xs:anyURI(concat(\"/db/view_triggers\",\"/\",\"test_trigger.xql\")), \"u+x,g+x,o+x\")\n" +
//                "    )\n" +
//                "else\n" +
//                "false()";
//        return util.stringResultQuery(details, query);
//    }
}
