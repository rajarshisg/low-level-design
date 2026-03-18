package model;

public class Address {
    private final String text;
    private final int pinCode;
    private final String city;
    private final Location location;

    public Address(String text, int pinCode, String city, Location location) {
        this.text = text;
        this.pinCode = pinCode;
        this.city = city;
        this.location = location;
    }

    public String getText() {
        return text;
    }

    public int getPinCode() {
        return pinCode;
    }

    public String getCity() {
        return city;
    }

    public Location getLocation() {
        return location;
    }
}
