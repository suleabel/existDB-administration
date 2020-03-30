package hu.sule.administration.model;

import lombok.*;

import java.util.ArrayList;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class EditTriggerModel extends TriggerModel {
    private String path;
    private String fName;

    public EditTriggerModel(ArrayList<String> event, String tClass, String name, String value, String path, String fName) {
        super(event, tClass, name, value);
        this.path = path;
        this.fName = fName;
    }
}
