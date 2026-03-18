package repository;

import models.Fare;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class FareRepository implements Repository<Fare> {
    ConcurrentHashMap<String, Fare> fares;

    public FareRepository() {
        this.fares = new ConcurrentHashMap<>();
    }

    public void insert(Fare fare) {
        if (fares.containsKey(fare.getId())) {
            System.out.println("[ERROR] Cannot insert: Fare with ID " + fare.getId() + " already exists!");
            return;
        }
        fares.put(fare.getId(), fare);
    }

    public void delete(String id) {
        if (!fares.containsKey(id)) {
            System.out.println("[ERROR] Cannot delete: Fare with ID " + id + " does not exist!");
            return;
        }

        fares.remove(id);
    }

    public List<Fare> getAll() {
        return fares
                .keySet()
                .stream()
                .map(id -> fares.get(id))
                .toList();
    }

    public Fare getById(String id) {
        if (!fares.containsKey(id)) {
            System.out.println("[ERROR] Cannot find: Fare with ID " + id + " does not exist!");
            return null;
        }

        return fares.get(id);
    }
}
