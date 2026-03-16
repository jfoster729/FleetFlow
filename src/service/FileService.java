package service;

import model.Driver;
import model.Load;
import model.LoadStatus;
import model.Truck;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {

    public void saveDrivers(List<Driver> drivers, String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Driver driver : drivers) {
                writer.println(
                        driver.getDriverId() + "," +
                                driver.getName() + "," +
                                driver.getPhone() + "," +
                                driver.getLicenseNumber() + "," +
                                driver.isAvailable() + "," +
                                driver.getAssignedTruckId()
                );
            }
        } catch (IOException e) {
            System.out.println("Error saving drivers: " + e.getMessage());
        }
    }

    public List<Driver> loadDrivers(String fileName) {
        List<Driver> drivers = new ArrayList<>();
        File file = new File(fileName);

        if (!file.exists()) {
            return drivers;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                int driverId = Integer.parseInt(parts[0]);
                String name = parts[1];
                String phone = parts[2];
                String license = parts[3];
                boolean available = Boolean.parseBoolean(parts[4]);
                int assignedTruckId = Integer.parseInt(parts[5]);

                Driver driver = new Driver(driverId, name, phone, license);
                driver.setAvailable(available);
                driver.setAssignedTruckId(assignedTruckId);

                drivers.add(driver);
            }
        } catch (IOException e) {
            System.out.println("Error loading drivers: " + e.getMessage());
        }

        return drivers;
    }

    public void saveTrucks(List<Truck> trucks, String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Truck truck : trucks) {
                writer.println(
                        truck.getTruckId() + "," +
                                truck.getPlateNumber() + "," +
                                truck.getModel() + "," +
                                truck.getMaxLoadWeight() + "," +
                                truck.isAvailable()
                );
            }
        } catch (IOException e) {
            System.out.println("Error saving trucks: " + e.getMessage());
        }
    }

    public List<Truck> loadTrucks(String fileName) {
        List<Truck> trucks = new ArrayList<>();
        File file = new File(fileName);

        if (!file.exists()) {
            return trucks;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                int truckId = Integer.parseInt(parts[0]);
                String plateNumber = parts[1];
                String model = parts[2];
                double maxLoadWeight = Double.parseDouble(parts[3]);
                boolean available = Boolean.parseBoolean(parts[4]);

                Truck truck = new Truck(truckId, plateNumber, model, maxLoadWeight);
                truck.setAvailable(available);

                trucks.add(truck);
            }
        } catch (IOException e) {
            System.out.println("Error loading trucks: " + e.getMessage());
        }

        return trucks;
    }

    public void saveLoads(List<Load> loads, String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Load load : loads) {
                writer.println(
                        load.getLoadId() + "," +
                                load.getPickupLocation() + "," +
                                load.getDeliveryLocation() + "," +
                                load.getRate() + "," +
                                load.getWeight() + "," +
                                load.getStatus() + "," +
                                valueOrNull(load.getAssignedDriverId()) + "," +
                                valueOrNull(load.getAssignedTruckId())
                );
            }
        } catch (IOException e) {
            System.out.println("Error saving loads: " + e.getMessage());
        }
    }

    public List<Load> loadLoads(String fileName) {
        List<Load> loads = new ArrayList<>();
        File file = new File(fileName);

        if (!file.exists()) {
            return loads;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                int loadId = Integer.parseInt(parts[0]);
                String pickup = parts[1];
                String delivery = parts[2];
                double rate = Double.parseDouble(parts[3]);
                double weight = Double.parseDouble(parts[4]);
                LoadStatus status = LoadStatus.valueOf(parts[5]);

                Integer driverId = parseNullableInt(parts[6]);
                Integer truckId = parseNullableInt(parts[7]);

                Load load = new Load(loadId, pickup, delivery, rate, weight);
                load.setStatus(status);
                load.setAssignedDriverId(driverId);
                load.setAssignedTruckId(truckId);

                loads.add(load);
            }
        } catch (IOException e) {
            System.out.println("Error loading loads: " + e.getMessage());
        }

        return loads;
    }

    private String valueOrNull(Integer value) {
        return value == null ? "null" : String.valueOf(value);
    }

    private Integer parseNullableInt(String value) {
        return "null".equals(value) ? null : Integer.parseInt(value);
    }
}