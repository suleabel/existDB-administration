package hu.sule.administration.model;

import java.io.Serializable;
import java.util.List;

public class ExistDBUser implements Serializable {
    private String username;
    private List<String> groups;
    private String umask;
    private String primaryGroup;
    private String fullName;
    private String desc;
    private String password;
    private boolean Default;
    private boolean enabled = true;

    public ExistDBUser() {
    }

    public ExistDBUser(String username, String umask, String primaryGroup, String fullName, String desc, boolean aDefault, boolean enabled) {
        this.username = username;
        this.umask = umask;
        this.primaryGroup = primaryGroup;
        this.fullName = fullName;
        this.desc = desc;
        Default = aDefault;
        this.enabled = enabled;
    }

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
        return desc;
    }

    public void setDesc(String desc) {
        desc = desc;
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
                ", Desc='" + desc + '\'' +
                ", password='" + password + '\'' +
                ", Default=" + Default +
                ", enabled=" + enabled +
                '}';
    }
}
