xquery version "3.1";
import module namespace sm="http://exist-db.org/xquery/securitymanager";
if(xmldb:login("/db" , "admin", "admin1234")) then
sm:is-account-enabled('asasdasdd')
else
false()