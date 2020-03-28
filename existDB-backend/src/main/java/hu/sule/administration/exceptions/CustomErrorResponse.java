package hu.sule.administration.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class CustomErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    private int status;
    private String exceptionType;
    private String subtype;
    private String location;
    private String message;
    private String url;

    public CustomErrorResponse(LocalDateTime timestamp, int status, String exceptionType, String message, String url) {
        this.timestamp = timestamp;
        this.status = status;
        this.exceptionType = exceptionType;
        this.message = message;
        this.url = url;
    }

    public CustomErrorResponse(LocalDateTime timestamp, int status, String exceptionType, String subtype, String location, String message, String url) {
        this.timestamp = timestamp;
        this.status = status;
        this.exceptionType = exceptionType;
        this.subtype = subtype;
        this.location = location;
        this.message = message;
        this.url = url;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "CustomErrorResponse{" +
                "timestamp=" + timestamp +
                ", status=" + status +
                ", exceptionType='" + exceptionType + '\'' +
                ", subtype='" + subtype + '\'' +
                ", location='" + location + '\'' +
                ", message='" + message + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
