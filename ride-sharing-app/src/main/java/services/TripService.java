package services;

import models.*;
import repository.FareRepository;
import repository.TripRepository;
import strategies.DistanceCalculationStrategy;
import strategies.PricingStrategy;

import java.util.HashMap;
import java.util.List;

public class TripService {
    private final TripRepository tripRepository;
    private final FareRepository fareRepository;

    public TripService() {
        this.tripRepository = new TripRepository();
        this.fareRepository = new FareRepository();
    }

    public void create(String id, User user, Rider rider, Product product, Location source, Location destination, String otp, Fare estimatedFare) {
        if (user == null || rider == null || product == null || source == null || destination == null) {
            System.out.println("[ERROR] Could not create Trip as mandatory details are missing!");
        }

        Trip trip = (new Trip.TripBuilder())
                .id(id)
                .user(user)
                .rider(rider)
                .product(product)
                .source(source)
                .destination(destination)
                .otp(otp)
                .estimatedFare(estimatedFare)
                .build();

        this.tripRepository.insert(trip);
    }

    public void delete(String id) {
        this.tripRepository.delete(id);
    }

    public Trip getTrip(String id) {
        return this.tripRepository.getById(id);
    }

    public List<Trip> getAllTrips() {
        return this.tripRepository.getAll();
    }

    public List<Trip> getAllTripsForUser(User user) {
        List<Trip> allTrips = getAllTrips();

        return allTrips
                .stream()
                .filter(trip -> trip.getUser().equals(user))
                .toList();
    }

    public List<Trip> getAllTripsForRider(Rider rider) {
        List<Trip> allTrips = getAllTrips();

        return allTrips
                .stream()
                .filter(trip -> trip.getRider().equals(rider))
                .toList();
    }

    public Fare getFare(String fareId) {
        return this.fareRepository.getById(fareId);
    }

    public HashMap<Product, Fare> getFareEstimates(User user, String fareId, double srcLat, double srcLong, double dstLat, double dstLong, DistanceCalculationStrategy distanceCalculationStrategy, PricingStrategy pricingStrategy) {
        HashMap<Product, Fare> res = new HashMap<>();

        Location source = new Location(srcLat, srcLong);
        Location destination = new Location(dstLat, dstLong);

        double distance = distanceCalculationStrategy.calculateDistanceInKm(source, destination);

        // Assuming avg speed of a vehicle is 35 KM/Hr, in reality this will also be a strategy to calculate ETA
        // Eg: Uber uses represents the world as a graph, where roads are edges and intersections are nodes, it's
        // weighted directed graph where edge weights change dynamically based on traffic, weather etc.
        // The ETA is calculated using A* (a modified form of Dijkstra) and passed further through an ML model
        double time = distance / 35.0d;

        BasicProduct basicProduct = BasicProduct.getInstance();
        PremiumProduct premiumProduct = PremiumProduct.getInstance();
        XLProduct xlProduct = XLProduct.getInstance();


        double basicProductPrice = pricingStrategy.calculatePrice(distance, basicProduct, time);
        double premiumProductPrice = pricingStrategy.calculatePrice(distance, premiumProduct, time);
        double xlProductPrice = pricingStrategy.calculatePrice(distance, xlProduct, time);

        Fare basicFare = new Fare(
                fareId + ":1",
                user,
                basicProduct,
                source,
                destination,
                basicProductPrice
        );
        Fare premiumFare = new Fare(
                fareId + ":2",
                user,
                premiumProduct,
                source,
                destination,
                premiumProductPrice
        );
        Fare xlFare = new Fare(
                fareId + ":3",
                user,
                xlProduct,
                source,
                destination,
                xlProductPrice
        );

        this.fareRepository.insert(basicFare);
        this.fareRepository.insert(premiumFare);
        this.fareRepository.insert(xlFare);

        res.put(basicProduct, basicFare);
        res.put(premiumProduct, premiumFare);
        res.put(xlProduct, xlFare);

        return res;
    }
}
