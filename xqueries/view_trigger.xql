xquery version "3.1";

module namespace trigger='http://exist-db.org/xquery/trigger';
declare namespace xmldb='http://exist-db.org/xquery/xmldb';

declare function trigger:after-update-document($uri as xs:anyURI) {
    if($uri eq "/db/testData/books.xml") then (
        local:gen-view($uri)
        )
        else false()
};

declare function local:gen-view($uri as xs:string) {
    let $log-collection := '/db'
    let $log := 'view-log.xml'
    let $log-uri := concat($log-collection, '/', $log)
    
    (:fullpath for the view:)
    let $view_full_path := '/db/testData/test_view.xml'
    
    (:path for view:)
    let $collection := '/db/testData'
    
    (:view doc name:)
    let $doc-name := 'test_view.xml'
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