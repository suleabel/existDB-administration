package com.example.demo.service;

import com.example.demo.model.ExistDetails;
import com.example.demo.model.ForStoreResource;
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
                "xmldb:get-child-collections(\"" + collection + "\")\n" +
                "else\n" +
                "false()";

        return util.stringResultQuery(details, query);
    }
    public String getCollectionResources(ExistDetails details, String collection){
        String query = "xquery version \"3.1\";\n" +
                "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                "if(xmldb:login(\"/db\",\"" + details.getUsername() + "\", \"" + details.getPassword() + "\" ,false())) then\n" +
                "xmldb:get-child-resources(\"" + collection + "\")\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String saveResource(ExistDetails details, ForStoreResource storeResource){
        String query = "xquery version \"3.1\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\",\"" + details.getUsername() + "\",\"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        xmldb:store(\"" + storeResource.getCollectionPath() + "\",\"" + storeResource.getResourceName() + "\"," + storeResource.getContent() + ")\n" +
                "    )\n" +
                "else\n" +
                "false()";
        System.out.println(query);
        return "dummy success (some issue with this method)";
        // return util.stringResultQuery(details, query);
    }


}
