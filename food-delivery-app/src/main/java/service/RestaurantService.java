package service;

import model.*;
import repository.RestaurantRepository;

public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public boolean registerRestaurant(String id, String name, Address address) {
        restaurantRepository.insert(new Restaurant(id, name, address));
        return true;
    }

    public Restaurant getRestaurant(String id) {
        return restaurantRepository.getById(id);
    }

    public void addFoodItem(String restaurantId, String name, double price, int quantity) {
        Restaurant restaurant = getRestaurant(restaurantId);

        FoodItem foodItem = new FoodItem(name, price);
        restaurant.getFoodMenu().add(foodItem);
        restaurant.getFoodManager().addQuantity(name, quantity);
    }
}