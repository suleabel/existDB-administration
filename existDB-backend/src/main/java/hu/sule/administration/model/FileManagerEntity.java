package hu.sule.administration.model;

public class FileManagerEntity {
    private boolean isFile;
    private String name;
    private String size;
    private String humanSize;
    private String modified;
    private boolean hidden;
    private boolean canRead;
    private boolean canWrite;

    public FileManagerEntity() {
    }

    public FileManagerEntity(boolean isFile, String name, String size, String humanSize, String modified, boolean hidden, boolean canRead, boolean canWrite) {
        this.isFile = isFile;
        this.name = name;
        this.size = size;
        this.humanSize = humanSize;
        this.modified = modified;
        this.hidden = hidden;
        this.canRead = canRead;
        this.canWrite = canWrite;
    }

    public boolean getIsFile() {
        return isFile;
    }

    public void setIsFile(boolean isFile) {
        this.isFile = isFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getHumanSize() {
        return humanSize;
    }

    public void setHumanSize(String humanSize) {
        this.humanSize = humanSize;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isCanRead() {
        return canRead;
    }

    public void setCanRead(boolean canRead) {
        this.canRead = canRead;
    }

    public boolean isCanWrite() {
        return canWrite;
    }

    public void setCanWrite(boolean canWrite) {
        this.canWrite = canWrite;
    }

    @Override
    public String toString() {
        return "FileManagerEntity{" +
                "isFile='" + isFile + '\'' +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", humanSize='" + humanSize + '\'' +
                ", modified='" + modified + '\'' +
                ", hidden=" + hidden +
                ", canRead=" + canRead +
                ", canWrite=" + canWrite +
                '}';
    }
}
