package model;

abstract class FoodItemDecorator implements Food {
    private FoodItem foodItem;

    public FoodItemDecorator(FoodItem foodItem) {
        this.foodItem = foodItem;
    }

    public String getName() {
        return foodItem.getName();
    }

    public double getPrice() {
        return foodItem.getPrice();
    }
}
