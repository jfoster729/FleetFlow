package model;

public class Truck {

    private int truckId;
    private String plateNumber;
    private String model;
    private double maxLoadWeight;
    private boolean available;

    public Truck(int truckId, String plateNumber, String model, double maxLoadWeight) {
        this.truckId = truckId;
        this.plateNumber = plateNumber;
        this.model = model;
        this.maxLoadWeight = maxLoadWeight;
        this.available = true;
    }

    public int getTruckId() {
        return truckId;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getModel() {
        return model;
    }

    public double getMaxLoadWeight() {
        return maxLoadWeight;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Truck ID: " + truckId +
                " | Plate: " + plateNumber +
                " | Model: " + model +
                " | Max Load Weight: " + maxLoadWeight +
                " | Available: " + available;
    }
}