package hu.sule.administration.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ExistDBUser implements Serializable {

    @XmlElement(name="username")
    private String username;

    @XmlElement(name="group")
    private List<String> groups;

    @XmlElement(name="umask")
    private String umask;

    @XmlElement(name="primaryGroups")
    private String primaryGroup;

    @XmlElement(name="fullName")
    private String fullName;

    @XmlElement(name="desc")
    private String Desc;

    private String password;

    private boolean Default;

    @XmlElement(name="isEnabled")
    private boolean enabled = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public String getGroupsAsString(){
        return String.join("\", \"", this.groups);
    }

    public String getGroupsAsString2(){
        return String.join(", ", this.groups);
    }

    @Override
    public String toString() {
        return "ExistDBUser{" +
                "username='" + username + '\'' +
                ", groups=" + groups +
                ", umask='" + umask + '\'' +
                ", primaryGroup='" + primaryGroup + '\'' +
                ", fullName='" + fullName + '\'' +
                ", Desc='" + Desc + '\'' +
                ", password='" + password + '\'' +
                ", Default=" + Default +
                ", enabled=" + enabled +
                '}';
    }
}
