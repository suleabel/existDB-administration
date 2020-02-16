package com.example.demo.domain;

public class ExistDetails {
    private String username = "default";
    private String password = "default";
    private String url = "xmldb:exist://192.168.1.127:8080/exist/xmlrpc";
    private String collection = "/db/";
    private final String driver = "org.exist.xmldb.DatabaseImpl";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getDriver() {
        return driver;
    }

    @Override
    public String toString() {
        return "ExistDetails{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                ", collection='" + collection + '\'' +
                ", driver='" + driver + '\'' +
                '}';
    }
}
