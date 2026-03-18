package model;

import strategy.BillCalculationStrategy;

import java.util.concurrent.ConcurrentHashMap;

public class OrderBill {
    private final ConcurrentHashMap<Food, Integer> orderDetails;
    private boolean isPaid;

    public OrderBill(ConcurrentHashMap<Food, Integer> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void markPaid() {
        this.isPaid = true;
    }

    public boolean getIsPaid() {
        return this.isPaid;
    }

    public double calculateBillTotal(BillCalculationStrategy billCalculationStrategy) {
        return billCalculationStrategy.calculate(orderDetails);
    }

    public ConcurrentHashMap<Food, Integer> getOrderDetails() {
        return this.orderDetails;
    }
}
