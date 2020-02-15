package com.example.demo.domain;

import java.util.List;

public class ExistDBUsers {

    private String username;
    private List<String> groups;
    private String umask;
    private String primaryGroup;

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

    @Override
    public String toString() {
        return "ExistDBUsers{" +
                "username='" + username + '\'' +
                ", groups=" + groups +
                ", umask='" + umask + '\'' +
                ", primaryGroup='" + primaryGroup + '\'' +
                '}';
    }
}
