package models;

import java.util.Objects;

public class Vehicle {
    private final String id;
    private final String number;
    private final String model;
    private final VehicleType vehicleType;

    public static class VehicleBuilder {
        private String id;
        private String number;
        private String model;
        private VehicleType vehicleType;

        public VehicleBuilder id(String id) {
            this.id = id;
            return this;
        }

        public VehicleBuilder number(String number) {
            this.number = number;
            return this;
        }

        public VehicleBuilder model(String model) {
            this.model = model;
            return this;
        }

        public VehicleBuilder vehicleType(VehicleType vehicleType) {
            this.vehicleType = vehicleType;
            return this;
        }

        public Vehicle build() {
            return new Vehicle(this);
        }
    }

    private Vehicle(VehicleBuilder builder) {
        this.id = builder.id;
        this.number = builder.number;
        this.model = builder.model;
        this.vehicleType = builder.vehicleType;
    }

    public String getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getModel() {
        return model;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) return false;
        if (o == this) return true;

        return getId().equals(((Vehicle) o).getId());

    }
}
