package strategy;

import model.Food;
import model.FoodItem;

import java.util.concurrent.ConcurrentHashMap;

public interface BillCalculationStrategy {
    public double calculate(ConcurrentHashMap<Food, Integer> orderDetails);
}
