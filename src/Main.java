import model.LoadStatus;
import service.DispatchManager;
import service.FileService;
import service.ReportService;
import util.InputHelper;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        DispatchManager manager = new DispatchManager();
        ReportService reports = new ReportService();
        FileService fileService = new FileService();

        manager.setDrivers(fileService.loadDrivers("drivers.csv"));
        manager.setTrucks(fileService.loadTrucks("trucks.csv"));
        manager.setLoads(fileService.loadLoads("loads.csv"));

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
            System.out.println("12. Save Data");
            System.out.println("13. Edit Driver");
            System.out.println("14. Edit Truck");
            System.out.println("15. Edit Load");
            System.out.println("16. Delete Driver");
            System.out.println("17. Delete Truck");
            System.out.println("18. Delete Load");
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
                        System.out.println("Assignment failed. Check IDs, availability, current assignment, or truck weight capacity.");
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

                case 12:
                    fileService.saveDrivers(manager.getDrivers(), "drivers.csv");
                    fileService.saveTrucks(manager.getTrucks(), "trucks.csv");
                    fileService.saveLoads(manager.getLoads(), "loads.csv");
                    System.out.println("Data saved successfully.");
                    break;

                case 13:
                    int editDriverId = InputHelper.readInt(scanner, "Driver ID to edit: ");
                    String newDriverName = InputHelper.readLine(scanner, "New Driver Name: ");
                    String newDriverPhone = InputHelper.readLine(scanner, "New Phone: ");
                    String newDriverLicense = InputHelper.readLine(scanner, "New License: ");

                    if (manager.editDriver(editDriverId, newDriverName, newDriverPhone, newDriverLicense)) {
                        System.out.println("Driver updated successfully.");
                    } else {
                        System.out.println("Driver not found.");
                    }
                    break;

                case 14:
                    int editTruckId = InputHelper.readInt(scanner, "Truck ID to edit: ");
                    String newPlate = InputHelper.readLine(scanner, "New Plate Number: ");
                    String newTruckModel = InputHelper.readLine(scanner, "New Truck Model: ");
                    double newTruckWeight = InputHelper.readDouble(scanner, "New Max Load Weight: ");

                    if (manager.editTruck(editTruckId, newPlate, newTruckModel, newTruckWeight)) {
                        System.out.println("Truck updated successfully.");
                    } else {
                        System.out.println("Truck not found.");
                    }
                    break;

                case 15:
                    int editLoadId = InputHelper.readInt(scanner, "Load ID to edit: ");
                    String newPickup = InputHelper.readLine(scanner, "New Pickup Location: ");
                    String newDelivery = InputHelper.readLine(scanner, "New Delivery Location: ");
                    double newRate = InputHelper.readDouble(scanner, "New Load Rate: ");
                    double newWeight = InputHelper.readDouble(scanner, "New Load Weight: ");

                    if (manager.editLoad(editLoadId, newPickup, newDelivery, newRate, newWeight)) {
                        System.out.println("Load updated successfully.");
                    } else {
                        System.out.println("Load not found or cannot be edited in its current status.");
                    }
                    break;

                case 16:
                    int deleteDriverId = InputHelper.readInt(scanner, "Driver ID to delete: ");

                    if (manager.deleteDriver(deleteDriverId)) {
                        System.out.println("Driver deleted successfully.");
                    } else {
                        System.out.println("Driver not found or currently assigned.");
                    }
                    break;

                case 17:
                    int deleteTruckId = InputHelper.readInt(scanner, "Truck ID to delete: ");

                    if (manager.deleteTruck(deleteTruckId)) {
                        System.out.println("Truck deleted successfully.");
                    } else {
                        System.out.println("Truck not found or currently assigned.");
                    }
                    break;

                case 18:
                    int deleteLoadId = InputHelper.readInt(scanner, "Load ID to delete: ");

                    if (manager.deleteLoad(deleteLoadId)) {
                        System.out.println("Load deleted successfully.");
                    } else {
                        System.out.println("Load not found or currently in transit.");
                    }
                    break;

                case 0:
                    fileService.saveDrivers(manager.getDrivers(), "drivers.csv");
                    fileService.saveTrucks(manager.getTrucks(), "trucks.csv");
                    fileService.saveLoads(manager.getLoads(), "loads.csv");
                    running = false;
                    System.out.println("Data saved. Exiting FleetFlow...");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }
}