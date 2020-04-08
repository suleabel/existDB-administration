xquery version '3.1';
import module namespace v='http://exist-db.org/versioning';
declare variable $doc := doc('/db/testData/note.xml');
declare variable $rev := xs:integer(2);
if(xmldb:login('/db/','admin','')) then
    (
        let $collection := util:collection-name($doc)
        let $doc-name := util:document-name($doc)
        let $version-collection := concat($v:VERSIONS_COLLECTION, $collection)
        let $base-name := $version-collection || '/' || $doc-name || '.base'
    return
        if (not(doc-available($base-name))) then
            ()
        else if (exists($rev)) then
            let $diff := (
                    for $version in
                	collection($version-collection)/v:version[v:properties[v:document = $doc-name][v:revision <= $rev]][v:diff]
					order by xs:long($version/v:properties/v:revision) descending
				    return
					   $version
				   )[1]
            return
                doc($base-name)
		else
			doc($base-name)
    )
else
false()