package models;

public class XLProduct implements Product {
    private static volatile XLProduct instance;

    public static XLProduct getInstance() {
        if (instance != null) return instance;

        synchronized (XLProduct.class) {
            if (instance == null) instance = new XLProduct();
            return instance;
        }
    }

    private  XLProduct() {}

    public double getBaseFare() {
        return 200.0d;
    }

    public double getRatePerKm() {
        return 25.0d;
    }

    public double getRatePerHour() {
        return 12.0d;
    }

    public String getKey() {
        return "XL_PRODUCT";
    }
}

