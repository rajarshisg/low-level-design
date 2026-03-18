package model;

import java.util.concurrent.atomic.AtomicBoolean;

public class DeliveryPartner {
    private final String id;
    private final String name;
    private final Rating rating;
    private Location location;
    private AtomicBoolean isAvailable;

    public DeliveryPartner(String id, String name) {
        this.id = id;
        this.name = name;
        this.rating = new Rating();
        this.isAvailable = new AtomicBoolean(false);
        this.location = new Location(0, 0);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Rating getRating() {
        return rating;
    }

    public Location getLocation() {
        return location;
    }

    public AtomicBoolean getIsAvailable() {
        return this.isAvailable;
    }
}
