xquery version "3.1";
declare variable $collection := "/db/system/config/db";
if(xmldb:login("/db/","admin","")) then
    (
        xmldb:remove($collection,"collection.xconf"),
        let $result := xmldb:store($collection,"collection.xconf","<collection xmlns='http://exist-db.org/collection-config/1.0'>
  <triggers>
    <trigger class='org.exist.extensions.exquery.restxq.impl.RestXqTrigger'/>
  </triggers>
</collection>")
        let $reindex :=
                    if (starts-with($collection, "/db/system/config")) then
                        substring-after($collection, "/db/system/config")
                    else
                        $collection
        return(           
            if(xmldb:reindex($reindex) and exists($result)) then 
                true()
            else 
                false()
            )
        )
else
false()