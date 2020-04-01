xquery version '3.1';
if(xmldb:login('/db' , 'admin', '')) then
    (
        backups:retrieve('export', 'FULL20200316-1919.ZIP')
    )
else
false()