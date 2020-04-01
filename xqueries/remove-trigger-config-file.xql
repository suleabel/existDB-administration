xquery version "3.1";
if(xmldb:login("/db","admin","admin1234")) then
    (
        xmldb:remove("/db/system/config/db/testData","collection.xconf")
    )
else
false()