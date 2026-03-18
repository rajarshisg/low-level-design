package model;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class FoodMenu {
    private final ConcurrentHashMap<String, Food> menuItems;

    public FoodMenu() {
        this.menuItems = new ConcurrentHashMap<>();
    }

    public void add(Food foodItem) {
        if (menuItems.containsKey(foodItem.getName())) {
            System.out.println("[ERROR] FoodMenu already has the item " + foodItem.getName());
            return;
        }

        menuItems.putIfAbsent(foodItem.getName(), foodItem);
    }

    public void update(Food foodItem) {
        if (!menuItems.containsKey(foodItem.getName())) {
            System.out.println("[ERROR] Cannot update in FoodMenu as the item " + foodItem.getName() + " is not present");
            return;
        }

        menuItems.put(foodItem.getName(), foodItem);
    }

    public void delete(Food foodItem) {
        if (!menuItems.containsKey(foodItem.getName())) {
            System.out.println("[ERROR] Cannot delete from FoodMenu as the item " + foodItem.getName() + " is not present");
            return;
        }
        menuItems.remove(foodItem.getName());
    }

    public ConcurrentHashMap<String, Food> getMenuItems() {
        return menuItems;
    }
}
