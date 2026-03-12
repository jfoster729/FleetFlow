package model;

public class Load {

    private int loadId;
    private String pickupLocation;
    private String deliveryLocation;
    private double rate;
    private double weight;
    private LoadStatus status;
    private Integer assignedDriverId;
    private Integer assignedTruckId;

    public Load(int loadId, String pickupLocation, String deliveryLocation, double rate, double weight) {
        this.loadId = loadId;
        this.pickupLocation = pickupLocation;
        this.deliveryLocation = deliveryLocation;
        this.rate = rate;
        this.weight = weight;
        this.status = LoadStatus.PENDING;
        this.assignedDriverId = null;
        this.assignedTruckId = null;
    }

    public int getLoadId() {
        return loadId;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public double getRate() {
        return rate;
    }

    public double getWeight() {
        return weight;
    }

    public LoadStatus getStatus() {
        return status;
    }

    public Integer getAssignedDriverId() {
        return assignedDriverId;
    }

    public Integer getAssignedTruckId() {
        return assignedTruckId;
    }

    public void setStatus(LoadStatus status) {
        this.status = status;
    }

    public void setAssignedDriverId(Integer assignedDriverId) {
        this.assignedDriverId = assignedDriverId;
    }

    public void setAssignedTruckId(Integer assignedTruckId) {
        this.assignedTruckId = assignedTruckId;
    }

    @Override
    public String toString() {
        return "Load ID: " + loadId +
                " | Pickup: " + pickupLocation +
                " | Delivery: " + deliveryLocation +
                " | Rate: $" + rate +
                " | Weight: " + weight +
                " | Status: " + status +
                " | Driver ID: " + assignedDriverId +
                " | Truck ID: " + assignedTruckId;
    }
}