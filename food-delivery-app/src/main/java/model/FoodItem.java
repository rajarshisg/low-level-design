package model;

public class FoodItem implements Food {
    private final String name;
    private final double price;
    private final Rating rating;

    public FoodItem(String name, double price) {
        this.name = name;
        this.price = price;
        this.rating = new Rating();
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Rating getRating() {
        return this.rating;
    }
}
