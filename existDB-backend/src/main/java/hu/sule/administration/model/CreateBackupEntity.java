package hu.sule.administration.model;

public class CreateBackupEntity {
    private boolean isZip;
    private boolean isIncremental;

    public CreateBackupEntity() {
    }

    public CreateBackupEntity(boolean isZip, boolean isIncremental) {
        this.isZip = isZip;
        this.isIncremental = isIncremental;
    }

    public boolean isZip() {
        return isZip;
    }

    public void setZip(boolean zip) {
        isZip = zip;
    }

    public boolean isIncremental() {
        return isIncremental;
    }

    public void setIncremental(boolean incremental) {
        isIncremental = incremental;
    }

    @Override
    public String toString() {
        return "CreateBackupEntity{" +
                "isZip=" + isZip +
                ", isIncremental=" + isIncremental +
                '}';
    }
}
