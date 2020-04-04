package hu.sule.administration.queries;

import hu.sule.administration.model.ViewCreateModel;
import org.springframework.stereotype.Component;

@Component
public class ExistDbViewQueries {

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
}
