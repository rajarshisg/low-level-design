package service;

import model.*;
import repository.OrderRepository;
import strategy.SimpleBillCalculationStrategy;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final RestaurantService restaurantService;
    private final DeliveryPartnerService deliveryPartnerService;

    public OrderService(OrderRepository orderRepository,
                        UserService userService,
                        RestaurantService restaurantService,
                        DeliveryPartnerService deliveryPartnerService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.deliveryPartnerService = deliveryPartnerService;
    }

    public Order createOrder(String orderId,
                             String userId,
                             String restaurantId,
                             HashMap<String, Integer> items) {

        User user = userService.getUser(userId);
        Restaurant restaurant = restaurantService.getRestaurant(restaurantId);

        ConcurrentHashMap<Food, Integer> orderDetails = new ConcurrentHashMap<>();

        for (String itemName : items.keySet()) {
            FoodItem item = (FoodItem) restaurant.getFoodMenu().getMenuItems().get(itemName);
            orderDetails.put(item, items.get(itemName));
        }

        OrderBill bill = new OrderBill(orderDetails);
        double total = bill.calculateBillTotal(new SimpleBillCalculationStrategy());

        System.out.println("[INFO] Order total = " + total);

        Order order = new Order(orderId, user, bill, restaurant);
        orderRepository.insert(order);

        // Send to restaurant queue
        restaurant.getRestaurantOrderManager().addOrder(order);

        return order;
    }

    public void processOrder(String restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurant(restaurantId);
        restaurant.getRestaurantOrderManager().processOrder();
    }

    public void assignDeliveryPartner(String orderId) {
        Order order = orderRepository.getById(orderId);

        if (order.getDeliveryPartner() != null) {
            System.out.println("[ERROR] Delivery partner is already assigned");
            return;
        }

        Location restaurantLocation = order.getRestaurant().getAddress().getLocation();

        DeliveryPartner partner =
                deliveryPartnerService.assignPartner(restaurantLocation);

        if (partner == null) {
            System.out.println("[ERROR] No delivery partner available");
            return;
        }

        if(!deliveryPartnerService.markUnavailable(partner.getId())) {
            System.out.println("[ERROR] No delivery partner available");
            return;
        }

        order.setDeliveryPartner(partner);


        System.out.println("[INFO] Assigned partner " + partner.getName());
    }

    public void markDelivered(String orderId) {
        Order order = orderRepository.getById(orderId);

        if (order.getOrderStatus() != OrderStatus.PREPARED) {
            System.out.println("[ERROR] Cannot deliver: order not prepared");
            return;
        }

        if (order.getDeliveryPartner() == null) {
            System.out.println("[ERROR] Cannot deliver: no partner assigned");
            return;
        }

        order.markOutForDelivery(); // for demo purpose
        order.markDelivered();
        deliveryPartnerService.markAvailable(order.getDeliveryPartner().getId());

        System.out.println("[INFO] Order delivered");
    }
}