xquery version "3.1";
declare variable $url := "/db/xqueries";
declare variable $collections := xmldb:get-child-collections($url);
declare variable $resources := xmldb:get-child-resources($url);
if(xmldb:login("/db","admin","")) then
    (
        <result>
            {for $collection in $collections
                let $fullPath := string-join(($url, '/' , $collection),"")
                let $permissions := sm:get-permissions($fullPath)/sm:permission
                let $owner := $permissions/@owner/string()
                let $group := $permissions/@group/string()
                let $canWrite := sm:has-access($fullPath, "w")
                let $mode :=  string($permissions/@mode)
                let $date := xmldb:last-modified($url, $collection)
                let $mime := xmldb:get-mime-type($fullPath)
                return (
                    <exist:collection name="{$collection}" path="{$url}" owner="{$owner}" group="{$group}" writeable="{$canWrite}" mode="{$mode}" mime="{$mime}" date="{format-dateTime($date, "[M00]/[D00]/[Y0000] [H00]:[m00]:[s00]")}" resource="false">
                    </exist:collection>
                )
            }   
            {for $resource in $resources
                let $fullPath := string-join(($url, '/' , $resource),"")
                let $permissions := sm:get-permissions($fullPath)/sm:permission
                let $owner := $permissions/@owner/string()
                let $group := $permissions/@group/string()
                let $canWrite := sm:has-access($fullPath, "w")
                let $mode :=  string($permissions/@mode)
                let $date := xmldb:last-modified($url, $resource)
                let $mime := xmldb:get-mime-type($fullPath)
                return (
                    <exist:resource name="{$resource}" path="{$url}" owner="{$owner}" group="{$group}" writeable="{$canWrite}" mode="{$mode}" mime="{$mime}" date="{format-dateTime($date, "[M00]/[D00]/[Y0000] [H00]:[m00]:[s00]")}" resource="true">
                    </exist:resource>
                )
            }
        </result>
    )
else
false()