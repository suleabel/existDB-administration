package hu.sule.administration.model;

import java.util.List;

public class TriggerModel {
    private List<String> event;
    private String tClass;
    private String name;
    private String value;

    public TriggerModel() {
    }

    public TriggerModel(List<String> event, String tClass, String name, String value) {
        this.event = event;
        this.tClass = tClass;
        this.name = name;
        this.value = value;
    }

    public List<String> getEvent() {
        return event;
    }

    public String getEventByComma() {
        return String.join(", ", this.event);
    }

    public void setEvent(List<String> event) {
        this.event = event;
    }

    public String gettClass() {
        return tClass;
    }

    public void settClass(String tClass) {
        this.tClass = tClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TriggerModel{" +
                "event=" + event +
                ", tClass='" + tClass + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
