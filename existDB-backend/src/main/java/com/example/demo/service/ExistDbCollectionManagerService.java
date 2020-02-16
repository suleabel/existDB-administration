package com.example.demo.service;

import com.example.demo.domain.ExistDetails;
import com.example.demo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExistDbCollectionManagerService {

    @Autowired
    private Util util;

    public String getCollectionContent(ExistDetails details, String collection){
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"/db\",\"" + details.getUsername() + "\", \"" + details.getPassword() + "\" ,false())) then\n" +
                "xmldb:get-child-collections(\"" + details.getCollection() + collection + "\")\n" +
                "else\n" +
                "false()";

        return util.stringResultQuery(details, query);
    }


}
