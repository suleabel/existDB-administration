package hu.sule.administration.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ExistDetails {
    private String username = "default";
    private String password = "default";
    private String ip;
    private String url = "xmldb:exist://" + ip + "/exist/xmlrpc";
    private String collection = "/db/";
    private final String driver = "org.exist.xmldb.DatabaseImpl";
}
