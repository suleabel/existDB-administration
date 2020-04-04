package hu.sule.administration.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
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

    public ExistDBUser(String username, String umask, String primaryGroup, String fullName, String desc, boolean aDefault, boolean enabled) {
        this.username = username;
        this.umask = umask;
        this.primaryGroup = primaryGroup;
        this.fullName = fullName;
        this.desc = desc;
        this.Default = aDefault;
        this.enabled = enabled;
    }
    public String getGroupsAsString(){
        return String.join("\", \"", this.groups);
    }
}
