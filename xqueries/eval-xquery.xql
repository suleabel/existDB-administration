xquery version '3.1';
if(xmldb:login('/db' , 'admin', '')) then
    (
        util:eval(xs:anyURI("/db/xqueries/getAllGroupDetail.xql"))
    )
else
false()eval-