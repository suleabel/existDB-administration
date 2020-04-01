xquery version "3.1";
declare variable $isBinary := xs:boolean(true);
if(xmldb:login("/db","admin","admin1234")) then
    (
        xmldb:remove("",""),
        if($isBinary) then
            (
                xmldb:store-as-binary("","",util:string-to-binary(""))
            )
            else
            (
                xmldb:store("","","")
            )
    )
else
false()