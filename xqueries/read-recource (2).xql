xquery version "3.1";
declare variable $file := xs:string("/db/xqueries/afasdf.xml");
if(xmldb:login("/db","admin","admin1234")) then
    (
        if(util:is-binary-doc($file)) then
            (
                 util:binary-to-string(util:binary-doc($file))
            )
            else
            (
                doc($file)
            )
    )
else
false()