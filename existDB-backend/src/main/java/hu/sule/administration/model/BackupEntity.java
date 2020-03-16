package hu.sule.administration.model;

public class BackupEntity {
    private String fileName;
    private String nrInSequence;
    private String date;
    private String incremental;
    private String previous;
    private boolean downloadable;
    private String downloadLink;

    public BackupEntity() {
    }

    public BackupEntity(String fileName, String nrInSequence, String date, String incremental, String previous, boolean downloadable, String downloadLink) {
        this.fileName = fileName;
        this.nrInSequence = nrInSequence;
        this.date = date;
        this.incremental = incremental;
        this.previous = previous;
        this.downloadable = downloadable;
        this.downloadLink = downloadLink;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public boolean isDownloadable() {
        return downloadable;
    }

    public void setDownloadable(boolean downloadable) {
        this.downloadable = downloadable;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getNrInSequence() {
        return nrInSequence;
    }

    public void setNrInSequence(String nrInSequence) {
        this.nrInSequence = nrInSequence;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIncremental() {
        return incremental;
    }

    public void setIncremental(String incremental) {
        this.incremental = incremental;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    @Override
    public String toString() {
        return "BackupEntity{" +
                "fileName='" + fileName + '\'' +
                ", nrInSequence='" + nrInSequence + '\'' +
                ", date='" + date + '\'' +
                ", incremental='" + incremental + '\'' +
                ", previous='" + previous + '\'' +
                ", downloadable=" + downloadable +
                ", downloadLink='" + downloadLink + '\'' +
                '}';
    }
}
