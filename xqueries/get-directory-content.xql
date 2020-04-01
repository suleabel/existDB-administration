xquery version "3.1";
import module namespace sm="http://exist-db.org/xquery/securitymanager";
declare variable $exist-home := system:get-exist-home();
if(xmldb:login("/db" , "admin", "")) then
    (
        file:list($exist-home)
    )
else
false()