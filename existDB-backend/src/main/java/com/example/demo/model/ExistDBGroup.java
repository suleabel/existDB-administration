package com.example.demo.model;

import java.util.List;

public class ExistDBGroup {

    private String groupName;
    private String groupManager;
    private String desc;
    private List<String> groupMembers;
    private boolean Default;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupManager() {
        return groupManager;
    }

    public void setGroupManager(String groupManager) {
        this.groupManager = groupManager;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<String> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public boolean isDefault() {
        return Default;
    }

    public void setDefault(boolean aDefault) {
        Default = aDefault;
    }

    @Override
    public String toString() {
        return "ExistDBGroup{" +
                "groupName='" + groupName + '\'' +
                ", groupManager='" + groupManager + '\'' +
                ", desc='" + desc + '\'' +
                ", groupMembers=" + groupMembers +
                ", Default=" + Default +
                '}';
    }
}
