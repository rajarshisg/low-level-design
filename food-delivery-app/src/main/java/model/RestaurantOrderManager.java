package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RestaurantOrderManager {
    private final Restaurant restaurant;
    private final Queue<Order> newOrdersQueue;
    private final ConcurrentHashMap<String, Order> inProcessOrders;

    public RestaurantOrderManager(Restaurant restaurant) {
        this.restaurant = restaurant;
        this.newOrdersQueue = new ConcurrentLinkedQueue<>();
        this.inProcessOrders = new ConcurrentHashMap<>();
    }

    public void addOrder(Order order) {
        this.newOrdersQueue.add(order);
    }

    public Order processOrder() {
        if (newOrdersQueue.isEmpty()) {
            System.out.println("[INFO] No new orders to process!");
            return null;
        }

        Order order = newOrdersQueue.poll();

        System.out.println("[INFO] Processing order " + order.getId());

        RestaurantFoodManager restaurantFoodManager = restaurant.getFoodManager();
        OrderBill orderBill = order.getOrderBill();
        boolean singleItemFailed = false;
        HashMap<Food, Integer> processedItems = new HashMap<>();

        for (Food foodItem : orderBill.getOrderDetails().keySet()) {
            int quantity = orderBill.getOrderDetails().get(foodItem);

            if (!restaurantFoodManager.decrementQuantity(foodItem.getName(), quantity)) {
                System.out.println("[ERROR] Could not fulfill " + foodItem.getName() + " in order " + order.getId() + " due to unavailability");
                singleItemFailed = true;
                break;
            } else {
                processedItems.put(foodItem, quantity);
            }
        }

        if (!singleItemFailed) {
            System.out.println("[INFO] Order " + order.getId() + " accepted and started preparing");
            order.accept();
            inProcessOrders.put(order.getId(), order);
            return null;
        }

        // restock items in case order cannot be processed
        for (Food item : processedItems.keySet()) {
            restaurantFoodManager.addQuantity(item.getName(), processedItems.get(item));
        }

        return order;
    }

    public Order getInProcessOrder(String orderId) {
        if (!inProcessOrders.containsKey(orderId)) return null;
        return inProcessOrders.get(orderId);
    }
}
