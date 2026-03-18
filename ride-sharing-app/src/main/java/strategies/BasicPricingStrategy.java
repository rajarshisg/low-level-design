package strategies;

import models.Location;
import models.Product;
import models.Trip;

public class BasicPricingStrategy implements PricingStrategy {
    public double calculatePrice(double distance, Product product, double time) {
        return product.getBaseFare()
                + product.getRatePerKm() * distance
                + product.getRatePerHour() * time;
    }
}
