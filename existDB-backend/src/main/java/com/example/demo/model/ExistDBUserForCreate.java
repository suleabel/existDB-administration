package com.example.demo.model;

import java.util.List;

public class ExistDBUserForCreate {

    private String username;
    private List<String> groups;
    private String password;
    private String primaryGroup;
    private String fullName;
    private String Desc;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getGroupsAsString(){
        return String.join(",", this.groups);
    }

    @Override
    public String toString() {
        return "ExistDBUserForCreate{" +
                "username='" + username + '\'' +
                ", groups=" + groups +
                ", password='" + password + '\'' +
                ", primaryGroup='" + primaryGroup + '\'' +
                ", fullName='" + fullName + '\'' +
                ", Desc='" + Desc + '\'' +
                '}';
    }
}