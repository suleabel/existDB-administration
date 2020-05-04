package hu.sule.administration.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExistDBGroup {

    private String groupName;
    private String groupManager;
    private String desc;
    private List<String> groupMembers;
    private boolean Default;

    public ExistDBGroup(String groupName, String groupManager, String desc, boolean aDefault) {
        this.groupName = groupName;
        this.groupManager = groupManager;
        this.desc = desc;
        Default = aDefault;
    }
}
