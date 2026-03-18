package repository;


import model.Restaurant;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RestaurantRepository implements Repository<Restaurant> {
    ConcurrentHashMap<String, Restaurant> restaurants;

    public RestaurantRepository() {
        this.restaurants = new ConcurrentHashMap<>();
    }

    public void insert(Restaurant restaurant) {
        if (restaurants.containsKey(restaurant.getId())) {
            System.out.println("[ERROR] Cannot insert: Restaurant with ID " + restaurant.getId() + " already exists!");
            return;
        }
        restaurants.put(restaurant.getId(), restaurant);
    }

    public void delete(String id) {
        if (!restaurants.containsKey(id)) {
            System.out.println("[ERROR] Cannot delete: Restaurant with ID " + id + " does not exist!");
            return;
        }

        restaurants.remove(id);
    }

    public List<Restaurant> getAll() {
        return restaurants
                .keySet()
                .stream()
                .map(id -> restaurants.get(id))
                .toList();
    }

    public Restaurant getById(String id) {
        if (!restaurants.containsKey(id)) {
            System.out.println("[ERROR] Cannot find: Restaurant with ID " + id + " does not exist!");
            return null;
        }

        return restaurants.get(id);
    }
}