package models;

public class PremiumProduct implements  Product {
    private static volatile PremiumProduct instance;

    public static PremiumProduct getInstance() {
        if (instance != null) return instance;

        synchronized (PremiumProduct.class) {
            if (instance == null) instance = new PremiumProduct();
            return instance;
        }
    }

    private  PremiumProduct() {}

    public double getBaseFare() {
        return 150.0d;
    }

    public double getRatePerKm() {
        return 20.0d;
    }

    public double getRatePerHour() {
        return 10.0d;
    }

    public String getKey() {
        return "PREMIUM_PRODUCT";
    }
}
