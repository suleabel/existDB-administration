package hu.sule.administration.model;

public class ExistCollectionManagerModel {
    private String name;
    private String path;
    private String owner;
    private String group;
    private String mode;
    private String date;
    private String mime;
    private String locked;
    private boolean resource;
    private boolean triggerConfigAvailable = false;


    public ExistCollectionManagerModel(String name, String path, String owner, String group, String mode, String date, String mime, String locked, boolean resource, boolean triggerConfigAvailable) {
        this.name = name;
        this.path = path;
        this.owner = owner;
        this.group = group;
        this.mode = mode;
        this.date = date;
        this.mime = mime;
        this.locked = locked;
        this.resource = resource;
        this.triggerConfigAvailable = triggerConfigAvailable;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isResource() {
        return resource;
    }

    public void setResource(boolean resource) {
        this.resource = resource;
    }

    public boolean isTriggerConfigAvailable() {
        return triggerConfigAvailable;
    }

    public void setTriggerConfigAvailable(boolean triggerConfigAvailable) {
        this.triggerConfigAvailable = triggerConfigAvailable;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    @Override
    public String toString() {
        return "ExistCollectionManagerModel{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", owner='" + owner + '\'' +
                ", group='" + group + '\'' +
                ", mode='" + mode + '\'' +
                ", date='" + date + '\'' +
                ", mime='" + mime + '\'' +
                ", locked='" + locked + '\'' +
                ", resource=" + resource +
                ", triggerConfigAvailable=" + triggerConfigAvailable +
                '}';
    }
}
