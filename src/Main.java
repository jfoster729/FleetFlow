import model.Load;
import model.LoadStatus;
import service.DispatchManager;
import service.ReportService;
import util.InputHelper;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        DispatchManager manager = new DispatchManager();
        ReportService reports = new ReportService();

        boolean running = true;

        while (running) {
            System.out.println("\n=== FleetFlow Dispatch System ===");
            System.out.println("1. Add Driver");
            System.out.println("2. Add Truck");
            System.out.println("3. Create Load");
            System.out.println("4. Assign Load");
            System.out.println("5. Update Load Status");
            System.out.println("6. View Drivers");
            System.out.println("7. View Trucks");
            System.out.println("8. View All Loads");
            System.out.println("9. View Active Loads");
            System.out.println("10. View Completed Loads");
            System.out.println("11. View Revenue Summary");
            System.out.println("0. Exit");

            int choice = InputHelper.readInt(scanner, "Select option: ");

            switch (choice) {
                case 1:
                    String name = InputHelper.readLine(scanner, "Driver Name: ");
                    String phone = InputHelper.readLine(scanner, "Phone: ");
                    String license = InputHelper.readLine(scanner, "License: ");
                    int newDriverId = manager.addDriver(name, phone, license).getDriverId();
                    System.out.println("Driver added successfully. Generated Driver ID: " + newDriverId);
                    break;

                case 2:
                    String plate = InputHelper.readLine(scanner, "Plate Number: ");
                    String model = InputHelper.readLine(scanner, "Truck Model: ");
                    double maxWeight = InputHelper.readDouble(scanner, "Max Load Weight: ");
                    int newTruckId = manager.addTruck(plate, model, maxWeight).getTruckId();
                    System.out.println("Truck added successfully. Generated Truck ID: " + newTruckId);
                    break;

                case 3:
                    String pickup = InputHelper.readLine(scanner, "Pickup Location: ");
                    String delivery = InputHelper.readLine(scanner, "Delivery Location: ");
                    double rate = InputHelper.readDouble(scanner, "Load Rate: ");
                    double loadWeight = InputHelper.readDouble(scanner, "Load Weight: ");
                    int newLoadId = manager.addLoad(pickup, delivery, rate, loadWeight).getLoadId();
                    System.out.println("Load created successfully. Generated Load ID: " + newLoadId);
                    break;

                case 4:
                    int loadId = InputHelper.readInt(scanner, "Load ID: ");
                    int driverId = InputHelper.readInt(scanner, "Driver ID: ");
                    int truckId = InputHelper.readInt(scanner, "Truck ID: ");

                    if (manager.assignLoad(loadId, driverId, truckId)) {
                        System.out.println("Load assigned successfully.");
                    } else {
                        System.out.println("Assignment failed. Check IDs, availability, or truck weight capacity.");
                    }
                    break;

                case 5:
                    int statusLoadId = InputHelper.readInt(scanner, "Load ID: ");
                    System.out.println("1. PENDING");
                    System.out.println("2. ASSIGNED");
                    System.out.println("3. IN_TRANSIT");
                    System.out.println("4. DELIVERED");
                    System.out.println("5. CANCELLED");

                    int statusChoice = InputHelper.readInt(scanner, "Choose new status: ");

                    if (statusChoice >= 1 && statusChoice <= 5) {
                        LoadStatus status = LoadStatus.values()[statusChoice - 1];
                        if (manager.updateLoadStatus(statusLoadId, status)) {
                            System.out.println("Load status updated successfully.");
                        } else {
                            System.out.println("Load not found.");
                        }
                    } else {
                        System.out.println("Invalid status choice.");
                    }
                    break;

                case 6:
                    reports.printDrivers(manager.getDrivers());
                    break;

                case 7:
                    reports.printTrucks(manager.getTrucks());
                    break;

                case 8:
                    reports.printLoads(manager.getLoads());
                    break;

                case 9:
                    reports.printLoads(manager.getActiveLoads());
                    break;

                case 10:
                    reports.printLoads(manager.getCompletedLoads());
                    break;

                case 11:
                    reports.printRevenueSummary(
                            manager.getActiveLoadRevenue(),
                            manager.getDeliveredRevenue()
                    );
                    break;

                case 0:
                    running = false;
                    System.out.println("Exiting FleetFlow...");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }
}