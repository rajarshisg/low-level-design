package model;


import exception.InvalidOrderStateChangeException;

public class Order {
    private final String id;
    private final User user;
    private final OrderBill orderBill;
    private final Restaurant restaurant;
    private DeliveryPartner deliveryPartner;
    private OrderStatus orderStatus;

    public Order(String id, User user, OrderBill orderBill, Restaurant restaurant) {
        this.id = id;
        this.user = user;
        this.orderBill = orderBill;
        this.restaurant = restaurant;
        this.orderStatus = OrderStatus.CREATED;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public OrderBill getOrderBill() {
        return orderBill;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public DeliveryPartner getDeliveryPartner() {
        return deliveryPartner;
    }

    public void setDeliveryPartner(DeliveryPartner deliveryPartner) {
        this.deliveryPartner = deliveryPartner;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void accept() {
        if (this.orderStatus != OrderStatus.CREATED) {
            throw new InvalidOrderStateChangeException("Order needs to be CREATED status before being marked as ACCEPTED");
        }
        this.orderStatus = OrderStatus.ACCEPTED;
    }

    public void markPrepared() {
        if (this.orderStatus != OrderStatus.ACCEPTED) {
            throw new InvalidOrderStateChangeException("Order needs to be ACCEPTED status before being marked as PREPARED");
        }
        this.orderStatus = OrderStatus.PREPARED;
    }

    public void markOutForDelivery() {
        if (this.orderStatus != OrderStatus.PREPARED) {
            throw new InvalidOrderStateChangeException("Order needs to be PREPARED status before being marked as OUT_FOR_DELIVERY");
        }
        this.orderStatus = OrderStatus.OUT_FOR_DELIVERY;
    }

    public void markDelivered() {
        if (this.orderStatus != OrderStatus.OUT_FOR_DELIVERY) {
            throw new InvalidOrderStateChangeException("Order needs to be OUT_FOR_DELIVERY status before being marked as DELIVERED");
        }
        this.orderStatus = OrderStatus.DELIVERED;
    }
}
