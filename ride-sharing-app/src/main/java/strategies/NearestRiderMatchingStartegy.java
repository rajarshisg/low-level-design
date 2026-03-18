package strategies;

import models.Location;
import models.Rider;
import models.Trip;

import java.util.List;

public class NearestRiderMatchingStartegy implements RiderMatchingStrategy {
    private final DistanceCalculationStrategy distanceCalculationStrategy;

    public NearestRiderMatchingStartegy(DistanceCalculationStrategy distanceCalculationStrategy) {
        this.distanceCalculationStrategy = distanceCalculationStrategy;
    }

    public Rider getRider(Location userLocation, List<Rider> riders) {
        List<Rider> availableRiders = riders.stream().filter(rider -> rider.getIsAvailable().get()).toList();

        double globalMaxDistance = Integer.MAX_VALUE * 1.0d;
        Rider chosenRider = null;

        for (Rider rider : availableRiders) {
            double distanceFromUser = distanceCalculationStrategy.calculateDistanceInKm(userLocation, rider.getCurrentLocation());
            if (globalMaxDistance > distanceFromUser) {
                globalMaxDistance = distanceFromUser;
                chosenRider = rider;
            }
        }

        return chosenRider;
    }
}
