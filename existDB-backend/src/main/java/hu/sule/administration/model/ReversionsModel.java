package hu.sule.administration.model;

public class ReversionsModel {
    private String revNo;
    private String date;
    private String user;

    public ReversionsModel(String revNo, String date, String user) {
        this.revNo = revNo;
        this.date = date;
        this.user = user;
    }

    public String getRevNo() {
        return revNo;
    }

    public void setRevNo(String revNo) {
        this.revNo = revNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ReversionsModel{" +
                "revNo='" + revNo + '\'' +
                ", date='" + date + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}
