package hu.sule.administration.model;

public class RestoreResByRevResponse {
    private String oldContent;
    private String newContent;

    public RestoreResByRevResponse() {
    }

    public RestoreResByRevResponse(String oldContent, String newContent) {
        this.oldContent = oldContent;
        this.newContent = newContent;
    }

    public String getOldContent() {
        return oldContent;
    }

    public void setOldContent(String oldContent) {
        this.oldContent = oldContent;
    }

    public String getNewContent() {
        return newContent;
    }

    public void setNewContent(String newContent) {
        this.newContent = newContent;
    }

    @Override
    public String toString() {
        return "RestoreResByRevResponse{" +
                "oldContent='" + oldContent + '\'' +
                ", newContent='" + newContent + '\'' +
                '}';
    }
}
