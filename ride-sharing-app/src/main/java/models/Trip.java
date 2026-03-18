package models;

import strategies.DistanceCalculationStrategy;
import strategies.PricingStrategy;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class Trip {
    private final String id;
    private final User user;
    private final Rider rider;
    private final Product product;
    private final Location source;
    private final Location destination;
    private TripStatus tripStatus;
    private final String otp;
    private final Fare estimatedFare;
    private double finalFare;
    private Instant startedAt;
    private Instant endedAt;

    public static class TripBuilder {
        private String id;
        private User user;
        private Rider rider;
        private Product product;
        private Location source;
        private Location destination;
        private String otp;
        private Fare estimatedFare;

        public TripBuilder id(String id) {
            this.id = id;
            return this;
        }

        public TripBuilder user(User user) {
            this.user = user;
            return this;
        }

        public TripBuilder rider(Rider rider) {
            this.rider = rider;
            return this;
        }

        public TripBuilder product(Product product) {
            this.product = product;
            return this;
        }

        public TripBuilder source(Location source) {
            this.source = source;
            return this;
        }

        public TripBuilder destination(Location destination) {
            this.destination = destination;
            return this;
        }

        public TripBuilder otp(String otp) {
            this.otp = otp;
            return this;
        }

        public TripBuilder estimatedFare(Fare estimatedFare) {
            this.estimatedFare = estimatedFare;
            return this;
        }

        public Trip build() {
            return new Trip(this);
        }
    }

    private Trip(TripBuilder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.rider = builder.rider;
        this.product = builder.product;
        this.source = builder.source;
        this.destination = builder.destination;
        this.otp = builder.otp;
        this.estimatedFare = builder.estimatedFare;
        this.tripStatus = TripStatus.CREATED;
    }

    public String getId() {
        return id;
    }

    public void startTrip() {
        this.tripStatus = TripStatus.STARTED;
        this.startedAt = Instant.now();
    }

    public void endTrip() {
        if (this.startedAt == null || this.startedAt.compareTo(Instant.now()) > 0) return;
        this.tripStatus = TripStatus.ENDED;
        this.endedAt = Instant.now();
    }

    public User getUser() {
        return user;
    }

    public Rider getRider() {
        return rider;
    }

    public TripStatus getTripStatus() {
        return tripStatus;
    }

    public String getOtp() {
        return otp;
    }

    public Fare getEstimatedFare() {
        return estimatedFare;
    }

    public double getFinalFare() {
        return finalFare;
    }

    public Location getSource() {
        return source;
    }

    public Location getDestination() {
        return destination;
    }

    public Product getProduct() {
        return product;
    }

    public void setFinalFare(double finalFare) {
        this.finalFare = finalFare;
    }

    public double getActualTripDuration() {
        if (this.startedAt == null || this.endedAt == null) return 0.0d;
        return (Duration.between(startedAt, endedAt).toHours());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) return false;
        if (o == this) return true;

        return getId().equals(((Trip) o).getId());

    }
}
