package models;

import java.util.Objects;

public class User {
    private final String id;
    private final String name;
    private double rating;

    public static class UserBuilder {
        private String id;
        private String name;

        public UserBuilder id(String id) {
            this.id = id;
            return this;
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    private User(UserBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.rating = 0.0d;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) return false;
        if (o == this) return true;

        return getId().equals(((User) o).getId());

    }
}
