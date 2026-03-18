package controller;

import model.*;
import service.*;

import java.util.HashMap;

public class FoodDeliveryAppController {

    private final UserService userService;
    private final RestaurantService restaurantService;
    private final OrderService orderService;
    private final DeliveryPartnerService deliveryPartnerService;

    public FoodDeliveryAppController(UserService userService,
                                     RestaurantService restaurantService,
                                     OrderService orderService,
                                     DeliveryPartnerService deliveryPartnerService) {
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.orderService = orderService;
        this.deliveryPartnerService = deliveryPartnerService;
    }

    public HashMap<String, Object> registerUser(String id, String name) {
        userService.registerUser(id, name);
        return success("User registered");
    }

    public HashMap<String, Object> registerRestaurant(String id, String name) {
        Address address = new Address("dummy", 700001, "Kolkata",
                new Location(10, 10));
        restaurantService.registerRestaurant(id, name, address);
        return success("Restaurant registered");
    }

    public HashMap<String, Object> addFood(String restaurantId, String name, double price, int qty) {
        restaurantService.addFoodItem(restaurantId, name, price, qty);
        return success("Food added");
    }

    public HashMap<String, Object> createOrder(String orderId,
                                               String userId,
                                               String restaurantId,
                                               HashMap<String, Integer> items) {
        orderService.createOrder(orderId, userId, restaurantId, items);
        return success("Order created");
    }

    public HashMap<String, Object> processOrder(String restaurantId) {
        orderService.processOrder(restaurantId);
        return success("Order processed");
    }

    public HashMap<String, Object> assignPartner(String orderId) {
        orderService.assignDeliveryPartner(orderId);
        return success("Partner assigned");
    }

    public HashMap<String, Object> markDelivered(String orderId) {
        orderService.markDelivered(orderId);
        return success("Order delivered");
    }

    private HashMap<String, Object> success(String msg) {
        HashMap<String, Object> res = new HashMap<>();
        res.put("status", "OK");
        res.put("message", msg);
        return res;
    }

    public HashMap<String, Object> markOrderPrepared(String restaurantId, String orderId) {
        Restaurant restaurant = restaurantService.getRestaurant(restaurantId);
        Order order = restaurant.getRestaurantOrderManager().getInProcessOrder(orderId);

        order.markPrepared();

        return success("Order marked prepared");
    }
}