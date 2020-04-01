xquery version "3.1";
declare variable $collection := "/db/testCollection";
declare variable $resource := "test.xml";
declare variable $data :="<a></a>";
declare variable $isBinary := false();
declare variable $mime := "application/xml";
declare variable $path := string-join(($collection, "/", $resource),"");
if(xmldb:login("/db/" , "admin", "")) then
    (
        if($isBinary) then
            xmldb:store-as-binary($collection, $resource, $data)
        else 
            (
            if (string-length($mime) ne 0) then
                xmldb:store($collection, $resource, $data, $mime)
            else
                xmldb:store($collection, $resource, $data)
            )
    )
else
false()