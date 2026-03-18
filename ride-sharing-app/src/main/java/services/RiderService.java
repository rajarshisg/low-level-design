package services;

import models.*;
import repository.RiderRepository;
import strategies.DistanceCalculationStrategy;
import strategies.NearestRiderMatchingStartegy;
import strategies.RiderMatchingStrategy;

import java.util.List;

public class RiderService {
    private final RiderRepository repository;
    private RiderMatchingStrategy riderMatchingStrategy;

    public RiderService(RiderMatchingStrategy riderMatchingStrategy) {
        this.repository = new RiderRepository();
        this.riderMatchingStrategy = riderMatchingStrategy;
    }

    public void create(String id, String name, Vehicle vehicle, List<Product> offeredProducts) {
        Rider rider = (new Rider.RiderBuilder())
                .id(id)
                .name(name)
                .vehicle(vehicle)
                .offeredProducts(offeredProducts)
                .build();

        this.repository.insert(rider);
    }

    public void delete(String id) {
        this.repository.delete(id);
    }

    public Rider getRider(String id) {
        return this.repository.getById(id);
    }

    public List<Rider> getAllRiders() {
        return this.repository.getAll();
    }

    public List<Rider> getAllAvailableRiders() {
        List<Rider> riders = this.repository.getAll();
        return riders
                .stream()
                .filter(rider -> rider.getIsAvailable().get())
                .toList();
    }

    public Rider getRiderForTrip(Location source) {
        return this.riderMatchingStrategy.getRider(source, getAllAvailableRiders());
    }

    public void setRiderMatchingStrategy(RiderMatchingStrategy riderMatchingStrategy) {
        this.riderMatchingStrategy = riderMatchingStrategy;
    }
}
