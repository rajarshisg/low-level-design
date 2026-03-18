package model;

public class Location {
    private long latitude;
    private long longitude;

    public Location(long latitude, long longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public long getLongitude() {
        return longitude;
    }
}
