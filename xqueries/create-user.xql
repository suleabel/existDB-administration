xquery version "3.1";
import module namespace sm="http://exist-db.org/xquery/securitymanager";
if(xmldb:login("/db/","admin","admin1234")) then
    (
        sm:create-account("eztkeresed", "asdasd", "dba", ("eXide", "guest", "monex", "dba"), "sdasd", "asdasdasd"),
        true()
    )
else
false()