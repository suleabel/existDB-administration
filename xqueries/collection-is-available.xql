xquery version "3.1";
if(xmldb:login("/db","admin","admin1234")) then
    (
        xmldb:collection-available("/db/system/config/db/generated-xml-schemas")
    )
else
false()