package models;

public class BasicProduct implements Product{
    private static volatile BasicProduct instance;

    public static BasicProduct getInstance() {
        if (instance != null) return instance;

        synchronized (BasicProduct.class) {
            if (instance == null) instance = new BasicProduct();
            return instance;
        }
    }

    private  BasicProduct() {}

    public double getBaseFare() {
        return 100.0d;
    }

    public double getRatePerKm() {
        return 15.0d;
    }

    public double getRatePerHour() {
        return 5.0d;
    }

    public String getKey() {
        return "BASIC_PRODUCT";
    }
}
