package com.example.demo.model;

import java.util.List;

public class ExistDBGroup {

    private String name;
    private String manager;
    private String desc;
    private List<String> members;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "ExistDBGroup{" +
                "name='" + name + '\'' +
                ", manager='" + manager + '\'' +
                ", desc='" + desc + '\'' +
                ", members=" + members +
                '}';
    }
}
