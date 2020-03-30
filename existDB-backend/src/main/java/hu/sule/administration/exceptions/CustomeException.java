package hu.sule.administration.exceptions;

public class CustomeException extends RuntimeException {

    private String location;
    private String subType;

    public CustomeException(String message, String location, String subType){
        super(message);
        this.location = location;
        this.subType = subType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }
}
