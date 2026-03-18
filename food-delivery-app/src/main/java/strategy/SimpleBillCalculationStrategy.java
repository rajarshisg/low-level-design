package strategy;

import model.Food;
import model.FoodItem;

import java.util.concurrent.ConcurrentHashMap;

public class SimpleBillCalculationStrategy implements BillCalculationStrategy {
    public double calculate(ConcurrentHashMap<Food, Integer> orderDetails) {
        double total = 0.0d;

        for (Food item : orderDetails.keySet()) {
            double itemPrice = item.getPrice();
            int quantity = orderDetails.get(item);

            total += itemPrice * quantity;
        }

        return total;
    }
}
