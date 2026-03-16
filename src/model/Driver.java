package model;

public class Driver {

    private int driverId;
    private String name;
    private String phone;
    private String licenseNumber;
    private boolean available;
    private int assignedTruckId;

    public Driver(int driverId, String name, String phone, String licenseNumber) {
        this.driverId = driverId;
        this.name = name;
        this.phone = phone;
        this.licenseNumber = licenseNumber;
        this.available = true;
        this.assignedTruckId = -1;
    }

    public int getDriverId() {
        return driverId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public boolean isAvailable() {
        return available;
    }

    public int getAssignedTruckId() {
        return assignedTruckId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setAssignedTruckId(int assignedTruckId) {
        this.assignedTruckId = assignedTruckId;
    }

    @Override
    public String toString() {
        return "Driver ID: " + driverId +
                " | Name: " + name +
                " | Phone: " + phone +
                " | License: " + licenseNumber +
                " | Available: " + available +
                " | Assigned Truck ID: " + assignedTruckId;
    }
}