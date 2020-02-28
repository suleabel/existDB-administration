package com.example.demo.model;

public class ForStoreResource {
    private String collectionPath;
    private String resourceName;
    private String content;

    public ForStoreResource(String collectionPath, String resourceName, String content) {
        this.collectionPath = collectionPath;
        this.resourceName = resourceName;
        this.content = content;
    }

    public String getCollectionPath() {
        return collectionPath;
    }

    public void setCollectionPath(String collectionPath) {
        this.collectionPath = collectionPath;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ForStoreResource{" +
                "collectionPath='" + collectionPath + '\'' +
                ", resourceName='" + resourceName + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
