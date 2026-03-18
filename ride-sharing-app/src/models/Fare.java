package models;

import java.time.Instant;
import java.time.temporal.TemporalAmount;

public class Fare {
    private final String id;
    private final User user;
    private final Product product;
    private final Location source;
    private final Location destination;
    private final double price;
    private final Instant expireAt;

    public Fare(String id, User user, Product product, Location source, Location destination, double price) {
        this.destination = destination;
        this.user = user;
        this.product = product;
        this.price = price;
        this.source = source;
        this.id = id;
        this.expireAt = Instant.now().plusSeconds(1); // fare valid for 1s for demo purpose, ideally Uber has it set to 2 mins
    }

    public String getId() {
        return id;
    }

    public Location getSource() {
        return source;
    }

    public Location getDestination() {
        return destination;
    }

    public double getPrice() {
        return price;
    }

    public boolean isExpired() {
        return Instant.now().compareTo(this.expireAt) > 0;
    }

    public User getUser() {
        return this.user;
    }

    public Product getProduct() {
        return product;
    }
}
