package hu.sule.administration.model;


import java.util.List;


// át ne merd rakni lombokosra mert meghal az egész cucc
public class EditTriggerModel {
    private String path;
    private String fName;
    private List<String> event;
    private String tClass;
    private String name;
    private String value;
    private String isOverwrite;

    public EditTriggerModel() {
    }

    public EditTriggerModel(String path, String fName, List<String> event, String tClass, String name, String value, String isOverwrite) {
        this.path = path;
        this.fName = fName;
        this.event = event;
        this.tClass = tClass;
        this.name = name;
        this.value = value;
        this.isOverwrite = isOverwrite;
    }

    public EditTriggerModel(List<String> event, String tClass, String name, String value) {
        this.event = event;
        this.tClass = tClass;
        this.name = name;
        this.value = value;
    }

    public String getIsOverwrite() {
        return isOverwrite;
    }

    public void setIsOverwrite(String isOverWrite) {
        this.isOverwrite = isOverWrite;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public List<String> getEvent() {
        return event;
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
        return "EditTriggerModel{" +
                "path='" + path + '\'' +
                ", fName='" + fName + '\'' +
                ", event=" + event +
                ", tClass='" + tClass + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", isOverwrite='" + isOverwrite + '\'' +
                '}';
    }

    public String getEventByComma() {
        return String.join(", ", this.event);
    }
}
