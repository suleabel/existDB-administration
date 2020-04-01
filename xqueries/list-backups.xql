xquery version "3.1";
declare variable $backupLocation := "/exist/data/export";
if(xmldb:login("/db/" , "admin", "")) then
    (
        backups:list($backupLocation)
    )
else
false()