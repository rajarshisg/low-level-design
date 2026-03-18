package strategies;

import models.Location;

// Basic Manhattan distance, better ones are Harvesine formula which takes into account curvature of earth
public class ManhattanDistanceCalculationStrategy implements DistanceCalculationStrategy {
    public double calculateDistanceInKm(Location source, Location destination) {
        // Manhattan distance between lat & long is in degrees and 1 degree is equivalent to 111 km
        return Math.sqrt(Math.pow(source.getLatitude() - destination.getLatitude(), 2)
                + Math.pow(source.getLongitude() - destination.getLongitude(), 2)) * 111;
    }
}
