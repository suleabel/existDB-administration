xquery version "3.1";
import module namespace v="http://exist-db.org/versioning" at "/db/system/repo/xquery-versioning-module-1.1.5/content/versioning.xqm";
if(xmldb:login("/db","admin","admin1234")) then
    (
        v:history(doc("/db/testData/test_data.xml"))
    )
else
false()