package hu.sule.administration.exceptions;

public class ApiException extends RuntimeException {

    private String location;
    private String subType;
    private StackTraceElement[] stackTraceElement;

    public ApiException(String message, String location, String subType){
        super(message);
        this.location = location;
        this.subType = subType;
    }

    public ApiException(String message, String location, String subType, StackTraceElement[] stackTraceElement){
        super(message);
        this.location = location;
        this.subType = subType;
        this.stackTraceElement = stackTraceElement;
    }

    public StackTraceElement[] getStackTraceElement() {
        return stackTraceElement;
    }

    public void setStackTraceElement(StackTraceElement[] stackTraceElement) {
        this.stackTraceElement = stackTraceElement;
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
