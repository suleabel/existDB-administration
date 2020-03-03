package com.example.demo.model;

public class ExistFileManagerModel {
    private String name;
    private String path;
    private String owner;
    private String group;
    private boolean writable = false;
    private String mode;
    private String date;
    private boolean resource;
    private boolean triggerConfigAvailable = false;

    public ExistFileManagerModel() {
    }

    public ExistFileManagerModel(String name, boolean resource) {
        this.name = name;
        this.resource = resource;
    }

    public ExistFileManagerModel(String name, String path, String owner, String group, boolean writable, String mode, String date, boolean resource, boolean triggerConfigAvailable) {
        this.name = name;
        this.path = path;
        this.owner = owner;
        this.group = group;
        this.writable = writable;
        this.mode = mode;
        this.date = date;
        this.resource = resource;
        this.triggerConfigAvailable = triggerConfigAvailable;
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

    public boolean isWritable() {
        return writable;
    }

    public void setWritable(boolean writable) {
        this.writable = writable;
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

    @Override
    public String toString() {
        return "ExistFileManagerModel{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", owner='" + owner + '\'' +
                ", group='" + group + '\'' +
                ", writable=" + writable +
                ", mode='" + mode + '\'' +
                ", date='" + date + '\'' +
                ", resource=" + resource +
                ", triggerConfigAvaliable=" + triggerConfigAvailable +
                '}';
    }
}
