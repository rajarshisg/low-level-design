package model;

public class Restaurant {
    private final String id;
    private final String name;
    private final Address address;
    private final Rating rating;
    private final FoodMenu foodMenu;
    private final RestaurantFoodManager foodManager;
    private final RestaurantOrderManager restaurantOrderManager;

    public Restaurant(String id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.rating = new Rating();
        this.foodMenu = new FoodMenu();
        this.foodManager = new RestaurantFoodManager(foodMenu);
        this.restaurantOrderManager = new RestaurantOrderManager(this);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public Rating getRating() {
        return rating;
    }

    public FoodMenu getFoodMenu() {
        return this.foodMenu;
    }

    public RestaurantFoodManager getFoodManager() {
        return this.foodManager;
    }

    public RestaurantOrderManager getRestaurantOrderManager() {
        return restaurantOrderManager;
    }
}
