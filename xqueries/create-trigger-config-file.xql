xquery version "3.1";
if(xmldb:login("/db","admin","admin1234")) then
    (
        xmldb:store("/db/system/config/db/testData","collection.xconf",
        <collection xmlns="http://exist-db.org/collection-config/1.0">
	    <triggers>			
	        <trigger class="org.exist.extensions.exquery.restxq.impl.RestXqTrigger"/>
	    </triggers>
        </collection>)
    )
else
false()