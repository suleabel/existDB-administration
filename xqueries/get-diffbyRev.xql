xquery version "3.1";
import module namespace v="http://exist-db.org/versioning";
if(xmldb:login("/db/","admin","")) then
    (
        v:diff(doc("/db/testData/note.xml"),2)
    )
else
false()