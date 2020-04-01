xquery version "3.1";
if(xmldb:login("/db" , "admin", "")) then
    (
        let $collection := "/db"
        let $reindex :=
                    if (starts-with($collection, "/db/system/config")) then
                        substring-after($collection, "/db/system/config")
                    else
                        $collection
                return
                    xmldb:reindex($reindex)
    )
else
false()