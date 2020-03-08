package hu.sule.administration.model;

public class ForStoreResourceAndColl {
    private String url;
    private String fileName;
    private String content;
    private boolean isBinary = true;

    public ForStoreResourceAndColl() {
    }

    public ForStoreResourceAndColl(String url, String fileName, String content) {
        this.url = url;
        this.fileName = fileName;
        this.content = content;
    }

    public ForStoreResourceAndColl(String url, String fileName, String content, boolean isBinary) {
        this.url = url;
        this.fileName = fileName;
        this.content = content;
        this.isBinary = isBinary;
    }

    public boolean isBinary() {
        return isBinary;
    }

    public void setBinary(boolean binary) {
        isBinary = binary;
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
        return "ForStoreResourceAndColl{" +
                "url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", content='" + content + '\'' +
                ", isBinary=" + isBinary +
                '}';
    }
}
