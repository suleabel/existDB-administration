package com.example.demo.model;

import java.util.List;

public class ExistDBUsers {

    private String username;
    private List<String> groups;
    private String umask;
    private String primaryGroup;
    private String fullName;
    private String Desc;
    private boolean Default;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public String getUmask() {
        return umask;
    }

    public void setUmask(String umask) {
        this.umask = umask;
    }

    public String getPrimaryGroup() {
        return primaryGroup;
    }

    public void setPrimaryGroup(String primaryGroup) {
        this.primaryGroup = primaryGroup;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public boolean isDefault() {
        return Default;
    }

    public void setDefault(boolean aDefault) {
        Default = aDefault;
    }

    @Override
    public String toString() {
        return "ExistDBUsers{" +
                "username='" + username + '\'' +
                ", groups=" + groups +
                ", umask='" + umask + '\'' +
                ", primaryGroup='" + primaryGroup + '\'' +
                ", fullName='" + fullName + '\'' +
                ", Desc='" + Desc + '\'' +
                ", Default=" + Default +
                '}';
    }
}
