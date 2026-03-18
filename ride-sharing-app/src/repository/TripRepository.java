package repository;

import models.Trip;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TripRepository implements Repository<Trip> {
    ConcurrentHashMap<String, Trip> trips;

    public TripRepository() {
        this.trips = new ConcurrentHashMap<>();
    }

    public void insert(Trip trip) {
        if (trips.containsKey(trip.getId())) {
            System.out.println("[ERROR] Cannot insert: Trip with ID " + trip.getId() + " already exists!");
            return;
        }
        trips.put(trip.getId(), trip);
    }

    public void delete(String id) {
        if (!trips.containsKey(id)) {
            System.out.println("[ERROR] Cannot delete: Trip with ID " + id + " does not exist!");
            return;
        }

        trips.remove(id);
    }

    public List<Trip> getAll() {
        return trips
                .keySet()
                .stream()
                .map(id -> trips.get(id))
                .toList();
    }

    public Trip getById(String id) {
        if (!trips.containsKey(id)) {
            System.out.println("[ERROR] Cannot find: Trip with ID " + id + " does not exist!");
            return null;
        }

        return trips.get(id);
    }
}
