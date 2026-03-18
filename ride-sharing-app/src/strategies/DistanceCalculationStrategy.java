package strategies;

import models.Location;

public interface DistanceCalculationStrategy {
    public double calculateDistanceInKm(Location source, Location destination);
}
