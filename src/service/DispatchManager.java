package service;

import model.Driver;
import model.Load;
import model.LoadStatus;
import model.Truck;

import java.util.ArrayList;
import java.util.List;

public class DispatchManager {

    private List<Driver> drivers = new ArrayList<>();
    private List<Truck> trucks = new ArrayList<>();
    private List<Load> loads = new ArrayList<>();

    private int nextDriverId = 1;
    private int nextTruckId = 1;
    private int nextLoadId = 1;

    public Driver addDriver(String name, String phone, String licenseNumber) {
        Driver driver = new Driver(nextDriverId++, name, phone, licenseNumber);
        drivers.add(driver);
        return driver;
    }

    public Truck addTruck(String plateNumber, String model, double maxLoadWeight) {
        Truck truck = new Truck(nextTruckId++, plateNumber, model, maxLoadWeight);
        trucks.add(truck);
        return truck;
    }

    public Load addLoad(String pickupLocation, String deliveryLocation, double rate, double weight) {
        Load load = new Load(nextLoadId++, pickupLocation, deliveryLocation, rate, weight);
        loads.add(load);
        return load;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public List<Truck> getTrucks() {
        return trucks;
    }

    public List<Load> getLoads() {
        return loads;
    }

    public Driver findDriverById(int id) {
        for (Driver driver : drivers) {
            if (driver.getDriverId() == id) {
                return driver;
            }
        }
        return null;
    }

    public Truck findTruckById(int id) {
        for (Truck truck : trucks) {
            if (truck.getTruckId() == id) {
                return truck;
            }
        }
        return null;
    }

    public Load findLoadById(int id) {
        for (Load load : loads) {
            if (load.getLoadId() == id) {
                return load;
            }
        }
        return null;
    }

    public boolean assignLoad(int loadId, int driverId, int truckId) {
        Load load = findLoadById(loadId);
        Driver driver = findDriverById(driverId);
        Truck truck = findTruckById(truckId);

        if (load == null || driver == null || truck == null) {
            return false;
        }

        if (!driver.isAvailable() || !truck.isAvailable()) {
            return false;
        }

        if (load.getWeight() > truck.getMaxLoadWeight()) {
            return false;
        }

        load.setAssignedDriverId(driverId);
        load.setAssignedTruckId(truckId);
        load.setStatus(LoadStatus.ASSIGNED);

        driver.setAvailable(false);
        driver.setAssignedTruckId(truckId);

        truck.setAvailable(false);

        return true;
    }

    public boolean updateLoadStatus(int loadId, LoadStatus newStatus) {
        Load load = findLoadById(loadId);

        if (load == null) {
            return false;
        }

        LoadStatus oldStatus = load.getStatus();
        load.setStatus(newStatus);

        if (newStatus == LoadStatus.DELIVERED || newStatus == LoadStatus.CANCELLED) {
            releaseDriverAndTruck(load);
        }

        if (oldStatus == LoadStatus.DELIVERED || oldStatus == LoadStatus.CANCELLED) {
            // no action needed for now
        }

        return true;
    }

    private void releaseDriverAndTruck(Load load) {
        Integer driverId = load.getAssignedDriverId();
        Integer truckId = load.getAssignedTruckId();

        if (driverId != null) {
            Driver driver = findDriverById(driverId);
            if (driver != null) {
                driver.setAvailable(true);
                driver.setAssignedTruckId(-1);
            }
        }

        if (truckId != null) {
            Truck truck = findTruckById(truckId);
            if (truck != null) {
                truck.setAvailable(true);
            }
        }
    }

    public List<Load> getActiveLoads() {
        List<Load> activeLoads = new ArrayList<>();

        for (Load load : loads) {
            if (load.getStatus() == LoadStatus.PENDING ||
                    load.getStatus() == LoadStatus.ASSIGNED ||
                    load.getStatus() == LoadStatus.IN_TRANSIT) {
                activeLoads.add(load);
            }
        }

        return activeLoads;
    }

    public List<Load> getCompletedLoads() {
        List<Load> completedLoads = new ArrayList<>();

        for (Load load : loads) {
            if (load.getStatus() == LoadStatus.DELIVERED) {
                completedLoads.add(load);
            }
        }

        return completedLoads;
    }

    public double getActiveLoadRevenue() {
        double total = 0.0;

        for (Load load : loads) {
            if (load.getStatus() == LoadStatus.PENDING ||
                    load.getStatus() == LoadStatus.ASSIGNED ||
                    load.getStatus() == LoadStatus.IN_TRANSIT) {
                total += load.getRate();
            }
        }

        return total;
    }

    public double getDeliveredRevenue() {
        double total = 0.0;

        for (Load load : loads) {
            if (load.getStatus() == LoadStatus.DELIVERED) {
                total += load.getRate();
            }
        }

        return total;
    }
}