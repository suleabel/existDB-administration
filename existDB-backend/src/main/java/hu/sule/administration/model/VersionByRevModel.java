package hu.sule.administration.model;

public class VersionByRevModel {
    private String revNo;
    private String path;

    public VersionByRevModel() {
    }

    public VersionByRevModel(String revNo, String path) {
        this.revNo = revNo;
        this.path = path;
    }

    public String getRevNo() {
        return revNo;
    }

    public void setRevNo(String revNo) {
        this.revNo = revNo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "VersionByRevModel{" +
                "revNo='" + revNo + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
