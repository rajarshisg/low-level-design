package strategies;

import models.Product;
import models.Trip;

public class PeakHourPricingStrategy implements  PricingStrategy {
    private final double peakFactor;

    public PeakHourPricingStrategy(double peakFactor, DistanceCalculationStrategy distanceCalculationStrategy) {
        this.peakFactor = peakFactor;
    }

    public double calculatePrice(double distance, Product product, double time) {
        double basePrice = product.getBaseFare()
                + product.getRatePerKm() * distance
                + product.getRatePerHour() * time;

        return (1 + peakFactor) * basePrice;
    }
}
