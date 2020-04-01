xquery version "3.1";
import module namespace sm="http://exist-db.org/xquery/securitymanager";
if(xmldb:login("/db","admin","admin1234")) then
sm:get-group-metadata-keys()
else
false()