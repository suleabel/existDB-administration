xquery version "3.1";
import module namespace usermanager="http://exist-db.org/apps/userManager" at "/db/apps/dashboard/bower_components/existdb-usermanager/modules/userManager.xqm";
if(xmldb:login("/db","admin","admin1234")) then
    (
       usermanager:list-users()
    )
else
false()