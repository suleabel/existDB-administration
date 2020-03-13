package hu.sule.administration.model;

import java.util.ArrayList;

public class EditTriggerModel extends TriggerModel {
    private String path;
    private String fName;

    public EditTriggerModel() {
        super();
    }

    public EditTriggerModel(ArrayList<String> event, String tClass, String name, String value, String path, String fName) {
        super(event, tClass, name, value);
        this.path = path;
        this.fName = fName;
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

    @Override
    public String toString() {
        return "EditTriggerModel{" +
                "path='" + path + '\'' +
                ", fName='" + fName + '\'' +
                '}';
    }
}
