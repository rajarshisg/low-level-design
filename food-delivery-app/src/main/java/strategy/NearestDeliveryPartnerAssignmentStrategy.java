package strategy;

import model.DeliveryPartner;
import model.Location;

import java.util.List;

public class NearestDeliveryPartnerAssignmentStrategy implements DeliveryPartnerAssignmentStrategy {
    private double calculateDistanceInKm(Location source, Location destination) {
        // Manhattan distance between lat & long is in degrees and 1 degree is equivalent to 111 km
        return Math.sqrt(Math.pow(source.getLatitude() - destination.getLatitude(), 2)
                + Math.pow(source.getLongitude() - destination.getLongitude(), 2)) * 111;
    }


    public DeliveryPartner getRider(Location restaurantLocation, List<DeliveryPartner> deliveryPartners) {
        List<DeliveryPartner> availableDeliveryPartners = deliveryPartners
                .stream()
                .filter(deliveryPartner -> deliveryPartner.getIsAvailable().get())
                .toList();

        double globalMaxDistance = Integer.MAX_VALUE * 1.0d;
        DeliveryPartner chosenDeliveryPartner = null;

        for (DeliveryPartner deliveryPartner : availableDeliveryPartners) {
            double distanceFromRestaurant = calculateDistanceInKm(restaurantLocation, deliveryPartner.getLocation());
            if (globalMaxDistance > distanceFromRestaurant) {
                globalMaxDistance = distanceFromRestaurant;
                chosenDeliveryPartner = deliveryPartner;
            }
        }

        return chosenDeliveryPartner;
    }
}
