xquery version "3.1";
if(xmldb:login("/db/" ,"admin", "")) then
    (
        system:import("exist/data/export/full20200328-1430","","")
    )
else
false()