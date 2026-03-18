package repository;

import models.Rider;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RiderRepository implements Repository<Rider> {
    ConcurrentHashMap<String, Rider> riders;

    public RiderRepository() {
        this.riders = new ConcurrentHashMap<>();
    }

    public void insert(Rider rider) {
        if (riders.containsKey(rider.getId())) {
            System.out.println("[ERROR] Cannot insert: Rider with ID " + rider.getId() + " already exists!");
            return;
        }
        riders.put(rider.getId(), rider);
    }

    public void delete(String id) {
        if (!riders.containsKey(id)) {
            System.out.println("[ERROR] Cannot delete: Rider with ID " + id + " does not exist!");
            return;
        }

        riders.remove(id);
    }

    public List<Rider> getAll() {
        return riders
                .keySet()
                .stream()
                .map(id -> riders.get(id))
                .toList();
    }

    public Rider getById(String id) {
        if (!riders.containsKey(id)) {
            System.out.println("[ERROR] Cannot find: Rider with ID " + id + " does not exist!");
            return null;
        }

        return riders.get(id);
    }
}
