xquery version "3.1";
import module namespace sm="http://exist-db.org/xquery/securitymanager";
if(xmldb:login("/db" , "admin", "")) then
    (
        file:read("/exist/LICENSE")
    )
else
false()