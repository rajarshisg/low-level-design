package model;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RestaurantFoodManager {
    private final FoodMenu foodMenu;
    private final ConcurrentHashMap<String, AtomicInteger> quantityMap;

    public RestaurantFoodManager(FoodMenu foodMenu) {
        this.foodMenu = foodMenu;
        this.quantityMap = new ConcurrentHashMap<>();
    }

    public boolean addQuantity(String foodName, int quantity) {
        if (!foodMenu.getMenuItems().containsKey(foodName)) {
            System.out.println("[ERROR] Menu does not have food " + foodName);
            return false;
        }

        if (quantity <= 0 || quantity > 100) {
            System.out.println("[ERROR] Quantity must be in [1,100]");
            return false;
        }

        AtomicInteger currQuantity =
                quantityMap.computeIfAbsent(foodName, k -> new AtomicInteger(0));

        synchronized (currQuantity) {
            currQuantity.addAndGet(quantity);
            return true;
        }

    }

    public boolean decrementQuantity(String foodName, int quantity) {
        if (!foodMenu.getMenuItems().containsKey(foodName)) {
            System.out.println("[ERROR] Decrementing food quantity: Menu does not have the food with name " + foodName);
            return false;
        }
        if (quantity < 0 || quantity > 100)  {
            System.out.println("[ERROR] Decrementing food quantity: Quantity to be added needs to be in range [1, 100] received " + quantity);
            return false;
        }

        AtomicInteger currQuantity = quantityMap.get(foodName);
        if (currQuantity == null) return false;

        synchronized (currQuantity) {
            int current = currQuantity.get();

            if (current < quantity) {
                return false;
            }

            currQuantity.set(current - quantity);
            quantityMap.put(foodName, currQuantity);

            return true;
        }

    }

    public boolean isAvailable(String foodName, int quantity) {
        if (!foodMenu.getMenuItems().containsKey(foodName)) {
            System.out.println("[ERROR] Checking food availability: Menu does not have the food with name " + foodName);
            return false;
        }

        AtomicInteger currQuantity = quantityMap.get(foodName);
        return currQuantity.get() > quantity;
    }
}
