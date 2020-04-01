xquery version "3.1";
if(xmldb:login("/db/","admin","")) then
    (
        file:serialize(<a></a>,"/exist/testdir/test.xml","")
    )
else
false()