package controller;

import models.*;
import services.RiderService;
import services.TripService;
import services.UserService;
import strategies.*;

import java.util.HashMap;
import java.util.List;

public class RideSharingController {
    private final RiderService riderService;
    private final TripService tripService;
    private final UserService userService;

    private final PricingStrategy pricingStrategy;
    private final RiderMatchingStrategy riderMatchingStrategy;
    private final DistanceCalculationStrategy distanceCalculationStrategy;

    private static volatile RideSharingController instance;


    public static  RideSharingController getInstance() {
        if (instance != null) return instance;

        synchronized (RideSharingController.class) {
            if (instance == null) instance = new RideSharingController();
            return instance;
        }
    }

    private RideSharingController() {
        this.distanceCalculationStrategy = new ManhattanDistanceCalculationStrategy();
        this.riderMatchingStrategy = new NearestRiderMatchingStartegy(this.distanceCalculationStrategy);
        this.pricingStrategy = new BasicPricingStrategy();

        this.riderService = new RiderService(riderMatchingStrategy);
        this.tripService = new TripService();
        this.userService = new UserService();
    }

    public void createRider(String id, String name, String vehicleId, String vehicleModel, String vehicleNumber, String vehicleTypeInput, List<String> offeredProductsInput) {
        VehicleType vehicleType = vehicleTypeInput.equals("TWO_WHEELER") ? VehicleType.TWO_WHEELER : VehicleType.FOUR_WHEELER;
        List<Product> offeredProducts = offeredProductsInput.stream().map(productInput -> {
            if (productInput.equals("XL")) return XLProduct.getInstance();
            else if (productInput.equals("PREMIUM")) return PremiumProduct.getInstance();
            else return BasicProduct.getInstance();
        }).toList();

        Vehicle vehicle = (new Vehicle.VehicleBuilder())
                .id(vehicleId)
                .number(vehicleNumber)
                .model(vehicleModel)
                .vehicleType(vehicleType)
                .build();

        this.riderService.create(id, name, vehicle, offeredProducts);

        System.out.println("[INFO] Rider with ID " + id + " created successfully!");
    }

    public void createUser(String id, String name) {
        this.userService.create(id, name);
        System.out.println("[INFO] User with ID " + id + " created successfully!");
    }

    public HashMap<String, HashMap<String, Double>> getFareEstimate(String userId, String fareId, double srcLat, double srcLong, double dstLat, double dstLong) {
        HashMap<String, HashMap<String, Double>> res = new HashMap<>();

        User user = this.userService.getUser(userId);
        if (user == null) return new HashMap<>();


        HashMap<Product, Fare> estimates = this.tripService.getFareEstimates(user, fareId, srcLat, srcLong, dstLat, dstLong, distanceCalculationStrategy, pricingStrategy);


        for (Product product : estimates.keySet()) {
            Fare fare = estimates.get(product);
            HashMap<String, Double> entry = new HashMap<>();
            entry.put(fare.getId(), fare.getPrice());
            res.put(product.getKey(), entry);
        }

        return res;
    }

    public HashMap<String, String> requestTrip(String userId, String fareId, String tripId, String otp) {
        User user = this.userService.getUser(userId);
        Fare fare = this.tripService.getFare(fareId);

        if (user == null || fare == null) return new HashMap<>();
        if (!fare.getUser().equals(user)) {
            System.out.println("[ERROR] Could not create a trip as the Fare wth ID " + fareId + " does not belong to the user " + userId + "!");
            return new HashMap<>();
        }

        if (fare.isExpired()) {
            System.out.println("[ERROR] Could not create a trip as the Fare wth ID " + fareId + " has expired!");
            return new HashMap<>();
        }

        Rider rider = this.riderService.getRiderForTrip(fare.getSource());
        if (rider == null) {
            System.out.println("[ERROR] Could not create a trip as no Rider was found!");
            return new HashMap<>();
        }

        if (!rider.getIsAvailable().compareAndSet(true, false)) {
            System.out.println("[ERROR] Could not create a trip as no Rider was found!");
            return new HashMap<>();
        }

        this.tripService.create(tripId, user, rider, fare.getProduct(), fare.getSource(), fare.getDestination(), otp, fare);

        HashMap<String, String> res = new HashMap<>();
        res.put("ID", tripId);
        res.put("User", user.getName());
        res.put("Rider", rider.getName());
        res.put("Product", fare.getProduct().getKey());
        res.put("Source", "Lat: " +
                Double.toString(fare.getSource().getLatitude()) + ", Long:" + Double.toString(fare.getSource().getLongitude()));
        res.put("Destination", "Lat: " +
                Double.toString(fare.getDestination().getLatitude()) + ", Long:" + Double.toString(fare.getDestination().getLongitude()));
        res.put("OTP", otp);
        res.put("Status", "CREATED");
        res.put("Estimated Fare", Double.toString(fare.getPrice()));

        return res;
    }

    public HashMap<String, String> updateTripStatus(String riderId, String tripId, TripStatus status, String otp) {
        Trip trip = this.tripService.getTrip(tripId);
        Rider rider = this.riderService.getRider(riderId);

        if (trip == null || rider == null) return new HashMap<>();

        if (!trip.getRider().equals(rider)) {
            System.out.println("[ERROR] Could not update trip status with ID " + tripId + " as the rider " + riderId + " does not belong to the trip!");
            return new HashMap<>();
        }

        if (!trip.getOtp().equals(otp)) {
            System.out.println("[ERROR] Could not update trip status with ID " + tripId + " as the OTP does not match");
            return new HashMap<>();
        }

        HashMap<String, String> res = new HashMap<>();
        res.put("ID", tripId);
        res.put("User", trip.getUser().getName());
        res.put("Rider", rider.getName());
        res.put("Product", trip.getProduct().getKey());
        res.put("Source", "Lat: " +
                Double.toString(trip.getSource().getLatitude()) + ", Long:" + Double.toString(trip.getSource().getLongitude()));
        res.put("Destination", "Lat: " +
                Double.toString(trip.getDestination().getLatitude()) + ", Long:" + Double.toString(trip.getDestination().getLongitude()));
        res.put("OTP", trip.getOtp());
        res.put("Estimated Fare", Double.toString(trip.getEstimatedFare().getPrice()));

        if (trip.getTripStatus() == TripStatus.CREATED && status == TripStatus.STARTED) {
            System.out.println("[INFO] Trip started!");
            trip.startTrip();
            res.put("Status", status.toString());
            return res;
        }

        if (trip.getTripStatus() == TripStatus.STARTED && status == TripStatus.ENDED) {
            System.out.println("[INFO] Trip completed!");

            trip.endTrip();
            rider.getIsAvailable().compareAndSet(false, true);

            double distance = distanceCalculationStrategy.calculateDistanceInKm(trip.getSource(), trip.getDestination());
            double finalFare = pricingStrategy.calculatePrice(distance, trip.getProduct(), trip.getActualTripDuration());

            trip.setFinalFare(finalFare);
            res.put("Status", status.toString());
            res.put("Final Fare", Double.toString(trip.getFinalFare()));
            return res;
        }

        System.out.println("[ERROR] Invalid Status change!");
        return new HashMap<>();
    }

    public void updateRiderLocation(String riderId, double latitude, double longitude) {
        Rider rider = this.riderService.getRider(riderId);

        if (rider == null) return;

        rider.setCurrentLocation(new Location(latitude, longitude));

        System.out.println("Updated Rider " + riderId + "'s current location [" + latitude + "," + longitude + "]");
    }
}
