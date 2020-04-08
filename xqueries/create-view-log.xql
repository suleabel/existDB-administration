xquery version "3.1";
if(xmldb:login("/db/","admin","")) then
    (
        let $view-log-collection := '/db'
        let $view-log := 'createdViews.xml'
        let $view-log-uri := concat($view-log-collection, '/', $view-log)
        return 
            (
                if (not(doc-available($view-log-uri))) then
                    xmldb:store($view-log-collection, $view-log, <views/>)
                else (),
            update insert <view config-location='configLocation' query-name='configLocation' view-location='viewLocation'/> into doc($view-log-uri)/views,
            true()
            )
    )
else
false()