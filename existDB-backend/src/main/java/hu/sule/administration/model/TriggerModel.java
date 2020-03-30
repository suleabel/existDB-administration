package hu.sule.administration.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TriggerModel {
    private List<String> event;
    private String tClass;
    private String name;
    private String value;

    public String getEventByComma() {
        return String.join(", ", this.event);
    }
}
