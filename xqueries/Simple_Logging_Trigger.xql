xquery version "1.0";  
  
(:  
    A simple XQuery for an XQueryTrigger that  
    logs all trigger events for which it is executed  
    in the file /db/triggersLogs.xml  
:)  
  
declare namespace  xmldb="http://exist-db.org/xquery/xmldb";  
  
declare variable $local:triggerEvent external;  
declare variable $local:eventType external;  
declare variable $local:collectionName external;  
declare variable $local:documentName external;  
declare variable $local:document external;  
  
declare variable $local:triggersLogFile := "triggersLog.xml";  
  
  
(: create the log file if it does not exist :)  
if(not(doc-available($local:triggersLogFile)))then  
(  
    xmldb:store("/db", $local:triggersLogFile, <triggers/>)  
)  
else(),  
  
(: log the trigger details to the log file :)  
update insert <trigger event="{$local:triggerEvent}" eventType="{$local:eventType}" collectionName="{$local:collectionName}" documentName="{$local:documentName}" timestamp="{current-dateTime()}">{$local:document}</trigger>  into doc("/db/triggersLog.xml")/triggers