package service;

import model.Driver;
import model.Load;
import model.Truck;

import java.util.List;

public class ReportService {

    public void printDrivers(List<Driver> drivers) {
        if (drivers.isEmpty()) {
            System.out.println("No drivers found.");
            return;
        }

        for (Driver driver : drivers) {
            System.out.println(driver);
        }
    }

    public void printTrucks(List<Truck> trucks) {
        if (trucks.isEmpty()) {
            System.out.println("No trucks found.");
            return;
        }

        for (Truck truck : trucks) {
            System.out.println(truck);
        }
    }

    public void printLoads(List<Load> loads) {
        if (loads.isEmpty()) {
            System.out.println("No loads found.");
            return;
        }

        for (Load load : loads) {
            System.out.println(load);
        }
    }

    public void printRevenueSummary(double activeRevenue, double deliveredRevenue) {
        System.out.println("Revenue Summary");
        System.out.println("Active Load Revenue: $" + activeRevenue);
        System.out.println("Delivered Revenue: $" + deliveredRevenue);
    }
}