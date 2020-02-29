package com.example.demo.model;

public class ExistFileManagerModel {
    private String name;
    private String owner;
    private String group;
    private boolean isWriteable;
    private String mode;
    private String date;
    private boolean isResource;

    public ExistFileManagerModel(String name, boolean isResource) {
        this.name = name;
        this.isResource = isResource;
    }

    public ExistFileManagerModel(String name, String owner, String group, boolean isWriteable, String mode, String date, boolean isResource) {
        this.name = name;
        this.owner = owner;
        this.group = group;
        this.isWriteable = isWriteable;
        this.mode = mode;
        this.date = date;
        this.isResource = isResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isWriteable() {
        return isWriteable;
    }

    public void setWriteable(boolean writeable) {
        isWriteable = writeable;
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
        return isResource;
    }

    public void setResource(boolean resource) {
        isResource = resource;
    }

    @Override
    public String toString() {
        return "ExistFileManagerModel{" +
                "name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", group='" + group + '\'' +
                ", isWriteable=" + isWriteable +
                ", mode='" + mode + '\'' +
                ", date='" + date + '\'' +
                ", isResource=" + isResource +
                '}';
    }
}
