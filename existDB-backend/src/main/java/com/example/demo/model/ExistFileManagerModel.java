package com.example.demo.model;

public class ExistFileManagerModel {
    private String name;
    private boolean isResource;

    public ExistFileManagerModel(String name, boolean isResource) {
        this.name = name;
        this.isResource = isResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                ", isResource=" + isResource +
                '}';
    }
}
