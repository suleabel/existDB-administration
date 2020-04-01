xquery version "3.1";
declare variable $collection := "/db/xqueries";
declare variable $resource := "storetest.xql";
declare variable $path := string-join(($collection, "/", $resource),"");
if(xmldb:login("/db" , "admin", "")) then
    (
       sm:chgrp($path,"admin"),
       sm:chown($path,"dba"),
       sm:chmod($path,"rwxr-xr-x"),
       true()
    )
else
false()