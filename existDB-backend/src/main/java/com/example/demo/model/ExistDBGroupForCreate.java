package com.example.demo.model;

public class ExistDBGroupForCreate {
    private String groupName;
    private String groupManager;
    private String desc;

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

    @Override
    public String toString() {
        return "ExistDBGroupForCreate{" +
                "groupName='" + groupName + '\'' +
                ", groupManager='" + groupManager + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
