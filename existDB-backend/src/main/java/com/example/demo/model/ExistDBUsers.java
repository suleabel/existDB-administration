package com.example.demo.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="users")
public class ExistDBUsers {
    private List<ExistDBUser> existDBUserList = new ArrayList<>();

    @XmlElement(name="user")
    public List<ExistDBUser> getExistDBUserList() {
        return existDBUserList;
    }

    public void setExistDBUserList(List<ExistDBUser> existDBUserList) {
        this.existDBUserList = existDBUserList;
    }
}
