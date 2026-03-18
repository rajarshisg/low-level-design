package strategies;

import models.Product;
import models.Trip;

public interface PricingStrategy {
    public double calculatePrice(double distance, Product product, double time);
}
