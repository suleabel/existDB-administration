xquery version "3.1";
import module namespace util="http://exist-db.org/xquery/util" at "java:org.exist.xquery.functions.util.UtilModule";
if(xmldb:login("/db","admin","admin1234")) then
    (
       util:binary-to-string(util:binary-doc("/db/xqueries/afaszvan"))
    )
else
false()