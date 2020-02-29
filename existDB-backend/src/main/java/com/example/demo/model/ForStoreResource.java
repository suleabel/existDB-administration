package com.example.demo.model;

public class ForStoreResource {
    private String url;
    private String fileName;
    private String content;

    public ForStoreResource(String url, String fileName, String content) {
        this.url = url;
        this.fileName = fileName;
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
                "url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
