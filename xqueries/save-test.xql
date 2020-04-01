xquery version "3.1";
declare variable $data :="afgwerdf";
if(xmldb:login("/db" , "admin", "")) then
    (
        if($data instance of xs:base64Binary) then
            true()
        else
            false()
    )
else
false()