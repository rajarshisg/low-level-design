package repository;


import model.Order;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class OrderRepository implements Repository<Order> {
    ConcurrentHashMap<String, Order> orders;

    public OrderRepository() {
        this.orders = new ConcurrentHashMap<>();
    }

    public void insert(Order order) {
        if (orders.containsKey(order.getId())) {
            System.out.println("[ERROR] Cannot insert: Order with ID " + order.getId() + " already exists!");
            return;
        }
        orders.put(order.getId(), order);
    }

    public void delete(String id) {
        if (!orders.containsKey(id)) {
            System.out.println("[ERROR] Cannot delete: Order with ID " + id + " does not exist!");
            return;
        }

        orders.remove(id);
    }

    public List<Order> getAll() {
        return orders
                .keySet()
                .stream()
                .map(id -> orders.get(id))
                .toList();
    }

    public Order getById(String id) {
        if (!orders.containsKey(id)) {
            System.out.println("[ERROR] Cannot find: Order with ID " + id + " does not exist!");
            return null;
        }

        return orders.get(id);
    }
}