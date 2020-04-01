xquery version "3.1";
if(xmldb:login("/db","admin","admin1234")) then
    (
        let $rsource := xs:anyURI("/db/system/config/db")
        let $permissions := sm:get-permissions($rsource)/sm:permission
        let $owner := $permissions/@owner/string()
        let $group := $permissions/@group/string()
        let $canWrite := sm:has-access($rsource, "w")
        let $mode :=  string($permissions/@mode)
        let $date := xmldb:last-modified("/db/system/config/db/", "collection.xconf")
        return ($owner, $group, $canWrite, $mode, format-dateTime($date, "[M00]/[D00]/[Y0000] [H00]:[m00]:[s00]"))
    )
else
false()