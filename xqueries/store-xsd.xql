xquery version "3.1";
if(xmldb:login("/db","admin","admin1234")) then
    (
        xmldb:store("/db/generated-xml-schemas","test.xsd", util:string-to-binary("
        <?xml version='1.0' encoding='UTF-8'?>
        <schema xmlns='http://www.w3.org/2001/XMLSchema' xmlns:xsd='http://www.w3.org/2001/XMLSchema'>
        <element name='catalog'>
        <complexType>
        <sequence>
        <element name='book' maxOccurs='unbounded'>
          <complexType>
            <sequence>
              <element name='author' type='string' />
              <element name='title' type='string' />
              <element name='genre' type='string' />
              <element name='price' type='float' />
              <element name='publish_date' type='date' />
              <element name='description' type='string' />
            </sequence>
            <attribute name='id' type='string' />
          </complexType>
        </element>
      </sequence>
    </complexType>
  </element>
</schema>
        "))
    )
else
false()