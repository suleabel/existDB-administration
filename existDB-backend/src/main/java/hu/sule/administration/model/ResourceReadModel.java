package hu.sule.administration.model;

public class ResourceReadModel {
    private String content;
    private boolean isBinary;

    public ResourceReadModel() {
    }

    public ResourceReadModel(String content, boolean isBinary) {
        this.content = content;
        this.isBinary = isBinary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getIsBinary() {
        return isBinary;
    }

    public void setIsBinary(boolean isBinary) {
        this.isBinary = isBinary;
    }

    @Override
    public String toString() {
        return "ResourceReadModel{" +
                "content='" + content + '\'' +
                ", isBinary='" + isBinary + '\'' +
                '}';
    }
}
