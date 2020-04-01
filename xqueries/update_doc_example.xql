xquery version "1.0";
if(xmldb:login("/db/","admin","")) then
    (
        let $doc := doc('/db/testData/books.xml')/bookstore
        let $update := update insert 
    <book category="CHILDREN">
        <title lang="en">Harry Potter12</title>
        <author>J K. Rowling</author>
        <year>20012</year>
        <price>9.99</price>
    </book>
into $doc
return 
    $doc
    )
else
false()