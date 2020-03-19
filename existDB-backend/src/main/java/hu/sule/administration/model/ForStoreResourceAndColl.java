package hu.sule.administration.model;

public class ForStoreResourceAndColl {
    private String url;
    private String fileName;
    private String content;
    private String mime;
    private boolean isBinary = true;

    public ForStoreResourceAndColl() {
    }

    public ForStoreResourceAndColl(String url, String fileName) {
        this.url = url;
        this.fileName = fileName;
    }

    public ForStoreResourceAndColl(String url, String fileName, String content, String mime, boolean isBinary) {
        this.url = url;
        this.fileName = fileName;
        this.content = content;
        this.mime = mime;
        this.isBinary = isBinary;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
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
                ", mime='" + mime + '\'' +
                ", isBinary=" + isBinary +
                '}';
    }
}
