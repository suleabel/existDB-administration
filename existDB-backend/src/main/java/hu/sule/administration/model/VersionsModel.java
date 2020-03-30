package hu.sule.administration.model;

import java.util.ArrayList;

public class VersionsModel {
    private String doc;
    private ArrayList<ReversionsModel> reversions;

    public VersionsModel() {
    }

    public VersionsModel(String doc, ArrayList<ReversionsModel> reversions) {
        this.doc = doc;
        this.reversions = reversions;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public ArrayList<ReversionsModel> getReversions() {
        return reversions;
    }

    public void setReversions(ArrayList<ReversionsModel> reversions) {
        this.reversions = reversions;
    }

}
