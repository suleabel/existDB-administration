xquery version "3.1";
import module namespace sm="http://exist-db.org/xquery/securitymanager";

declare variable $METADATA_DESCRIPTION_KEY := xs:anyURI("http://exist-db.org/security/description");

if(xmldb:login("/db","admin","admin1234")) then
sm:get-group-metadata("dba", $METADATA_DESCRIPTION_KEY)
else
false()