xquery version "3.1";
import module namespace v="http://exist-db.org/versioning";
if(xmldb:login("/db/","admin","")) then
    (
        v:history(doc("/db/triggers-log.xml"))
    )
else
false()