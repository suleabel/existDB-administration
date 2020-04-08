xquery version "3.1";
if(xmldb:login("/db/","admin","")) then
    (
        if(xmldb:collection-available("/db/view_triggers"))then
            xmldb:store("/db/view_triggers", "test_trigger.xql", <view></view>, "application/xquery")
        else(
            xmldb:create-collection("/db", "view_triggers"),
            xmldb:store("/db/view_triggers", "test_trigger.xql", <view></view>, "application/xquery")
        ),
        sm:chmod(xs:anyURI(concat("/db/view_triggers","/","test_trigger.xql")), "u+x,g+x,o+x")
    )
else
false()