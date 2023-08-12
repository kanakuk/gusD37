package model;

public class locationModel {
    private int total;
    private String location;

    public locationModel(int total, String location) {
        this.total = total;
        this.location = location;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
