xquery version "3.1";
declare variable $collection := "/db/xqueries";
declare variable $resource := "test2.xql";
declare variable $data :="<a></a>";
declare variable $isBinary := true();
declare variable $mime := "application/xquery";
declare variable $path := string-join(($collection, "/", $resource),"");
if(xmldb:login("/db" , "admin", "")) then
    (
            let $isNew := not(util:binary-doc-available($path)) and not(doc-available($path))
            return (
                if ($isNew) then
                    (
                    if($isBinary) then
                        xmldb:store-as-binary($collection, $resource, $data)
                    else 
                        if (string-length($mime) ne 0) then
                            xmldb:store($collection, $resource, $data, $mime)
                        else
                            xmldb:store($collection, $resource, $data)
                    )
                else
                    (
                    if(util:is-binary-doc($path)) then
                            xmldb:store-as-binary($collection, $resource, $data)
                    else 
                        if (string-length($mime) ne 0) then
                            xmldb:store($collection, $resource, $data, $mime)
                        else
                            xmldb:store($collection, $resource, $data)
                    ),
                if ($isBinary or $mime eq "application/xquery") then
                        sm:chmod(xs:anyURI($path), "u+x,g+x,o+x")
                        else 
                            false()
            )
    )
else
false()