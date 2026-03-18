package models;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class Rider {
    private final String id;
    private final String name;
    private final Vehicle vehicle;
    private final List<Product> offeredProducts;
    private Location currentLocation;
    private AtomicBoolean isAvailable;
    private double rating;

    public static class RiderBuilder {
        private String id;
        private String name;
        private Vehicle vehicle;
        private List<Product> offeredProducts;

        public RiderBuilder id(String id) {
            this.id = id;
            return this;
        }

        public RiderBuilder name(String name) {
            this.name = name;
            return this;
        }

        public RiderBuilder vehicle(Vehicle vehicle) {
            this.vehicle = vehicle;
            return this;
        }

        public RiderBuilder offeredProducts(List<Product> offeredProducts) {
            this.offeredProducts = offeredProducts;
            return this;
        }

        public Rider build() {
            return new Rider(this);
        }
    }

    private Rider(RiderBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.vehicle = builder.vehicle;
        this.offeredProducts = builder.offeredProducts;
        this.currentLocation = null;
        this.isAvailable = new AtomicBoolean(true);
        this.rating = 0.0d;
    }

    public String getId() {
        return this.id;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getName() {
        return name;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public List<Product> getOfferedProducts() {
        return offeredProducts;
    }

    public AtomicBoolean getIsAvailable() {
        return isAvailable;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) return false;
        if (o == this) return true;

        return getId().equals(((Rider) o).getId());

    }
}
