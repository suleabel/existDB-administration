xquery version "3.1";
module namespace trigger='http://exist-db.org/xquery/trigger';
declare namespace xmldb='http://exist-db.org/xquery/xmldb';

declare function trigger:after-update-document($uri as xs:anyURI) {
    local:log-event('after', 'update', 'document', $uri),
    if($uri eq "/db/testData/books.xml") then (
        local:log-event('after', 'update', 'document-fired', $uri)
        )
        else false()
};
declare function local:gen-view($uri as xs:string) {
    (:fullpath for the view:)
    let $view_full_path := '/db/test_view.xml'
    (:path for view:)
    let $collection := '/db'
    (:view doc name:)
    let $doc-name := 'test_view2.xml'
    (:query expression:)
    let $view_content := doc("/db/testData/books.xml")/bookstore/book[price<30]
    return
        (
        if (not(doc-available($view_full_path))) then
            xmldb:store($collection, $doc-name, <view>{$view_content}</view>)
        else (
            xmldb:remove($collection, $doc-name),
            xmldb:store($collection, $doc-name, <view>{$view_content}</view>)
            )
        )
};

declare function local:log-event($type as xs:string, $event as xs:string, $object-type as xs:string, $uri as xs:string) {
    let $log-collection := '/db'
    let $log := 'view-log2.xml'
    let $log-uri := concat($log-collection, '/', $log)
    return
        (
        (: create the log file if it does not exist :)
        if (not(doc-available($log-uri))) then
            xmldb:store($log-collection, $log, <triggers/>)
        else ()
        ,
        (: log the trigger details to the log file :)
        update insert <trigger event='{string-join(($type, $event, $object-type), '-')}' uri='{$uri}' timestamp='{current-dateTime()}'/> into doc($log-uri)/triggers
        )
};