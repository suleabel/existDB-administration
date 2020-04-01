xquery version "3.1";
import module namespace file="http://exist-db.org/xquery/file" at "java:org.exist.xquery.modules.file.FileModule";
declare variable $node := doc("/db/system/config/db/collection.xconf");
if(xmldb:login("/db/","admin","admin1234",false())) then
    (
       update insert 
            <trigger class="org.exist.collections.triggers.HistoryTrigger"/>
        into $node/triggers
    )
    else
    false()