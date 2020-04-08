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
        xmldb:store($collection, $doc-name, <view>{$view_content}</view>)
        )
};