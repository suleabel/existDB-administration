package hu.sule.administration.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExistCollectionManagerModel {
    private String name;
    private String path;
    private String owner;
    private String group;
    private String mode;
    private String date;
    private String mime;
    private boolean locked;
    private boolean resource;
}
