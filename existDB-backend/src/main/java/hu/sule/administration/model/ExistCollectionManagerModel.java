package hu.sule.administration.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ExistCollectionManagerModel {
    private String name;
    private String path;
    private String owner;
    private String group;
    private String mode;
    private String date;
    private String mime;
    private String locked;
    private boolean resource;
    private boolean triggerConfigAvailable = false;
}
