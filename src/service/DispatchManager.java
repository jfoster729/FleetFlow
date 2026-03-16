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

        if (load.getAssignedDriverId() != null || load.getAssignedTruckId() != null) {
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

        load.setStatus(newStatus);

        if (newStatus == LoadStatus.DELIVERED || newStatus == LoadStatus.CANCELLED) {
            releaseDriverAndTruck(load);
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

    public boolean editDriver(int driverId, String newName, String newPhone, String newLicense) {
        Driver driver = findDriverById(driverId);

        if (driver == null) {
            return false;
        }

        driver.setName(newName);
        driver.setPhone(newPhone);
        driver.setLicenseNumber(newLicense);
        return true;
    }

    public boolean editTruck(int truckId, String newPlate, String newModel, double newMaxLoadWeight) {
        Truck truck = findTruckById(truckId);

        if (truck == null) {
            return false;
        }

        truck.setPlateNumber(newPlate);
        truck.setModel(newModel);
        truck.setMaxLoadWeight(newMaxLoadWeight);
        return true;
    }

    public boolean editLoad(int loadId, String newPickup, String newDelivery, double newRate, double newWeight) {
        Load load = findLoadById(loadId);

        if (load == null) {
            return false;
        }

        if (load.getStatus() == LoadStatus.IN_TRANSIT || load.getStatus() == LoadStatus.DELIVERED) {
            return false;
        }

        load.setPickupLocation(newPickup);
        load.setDeliveryLocation(newDelivery);
        load.setRate(newRate);
        load.setWeight(newWeight);
        return true;
    }

    public boolean deleteDriver(int driverId) {
        Driver driver = findDriverById(driverId);

        if (driver == null) {
            return false;
        }

        if (!driver.isAvailable()) {
            return false;
        }

        return drivers.remove(driver);
    }

    public boolean deleteTruck(int truckId) {
        Truck truck = findTruckById(truckId);

        if (truck == null) {
            return false;
        }

        if (!truck.isAvailable()) {
            return false;
        }

        return trucks.remove(truck);
    }

    public boolean deleteLoad(int loadId) {
        Load load = findLoadById(loadId);

        if (load == null) {
            return false;
        }

        if (load.getStatus() == LoadStatus.IN_TRANSIT) {
            return false;
        }

        if (load.getAssignedDriverId() != null || load.getAssignedTruckId() != null) {
            releaseDriverAndTruck(load);
        }

        return loads.remove(load);
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

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
        updateNextDriverId();
    }

    public void setTrucks(List<Truck> trucks) {
        this.trucks = trucks;
        updateNextTruckId();
    }

    public void setLoads(List<Load> loads) {
        this.loads = loads;
        updateNextLoadId();
    }

    private void updateNextDriverId() {
        int maxId = 0;
        for (Driver driver : drivers) {
            if (driver.getDriverId() > maxId) {
                maxId = driver.getDriverId();
            }
        }
        nextDriverId = maxId + 1;
    }

    private void updateNextTruckId() {
        int maxId = 0;
        for (Truck truck : trucks) {
            if (truck.getTruckId() > maxId) {
                maxId = truck.getTruckId();
            }
        }
        nextTruckId = maxId + 1;
    }

    private void updateNextLoadId() {
        int maxId = 0;
        for (Load load : loads) {
            if (load.getLoadId() > maxId) {
                maxId = load.getLoadId();
            }
        }
        nextLoadId = maxId + 1;
    }
}