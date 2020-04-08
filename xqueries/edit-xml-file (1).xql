xquery version "3.1";
if(xmldb:login("/db/","admin","")) then
    (
        file:delete("/exist/testdir/test.xml"),
        file:serialize(<a><b/></a>,"/exist/testdir/test.xml","")
    )
else
false()