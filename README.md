# FleetFlow

FleetFlow is a Java-based dispatch and load tracking system designed for small trucking operations.

The application allows dispatchers to manage drivers, trucks, and delivery loads while tracking shipment status and revenue.

## Features

- Add and manage drivers
- Add and manage trucks
- Create delivery loads
- Assign loads to drivers and trucks
- Track load status (Pending, Assigned, In Transit, Delivered)
- View active and completed loads
- Revenue tracking for active and delivered loads

## Technologies Used

- Java
- Object-Oriented Programming
- Enums
- Collections (ArrayList)
- Modular Service Architecture

## Project Structure
src
├── model
│ ├── Driver.java
│ ├── Truck.java
│ ├── Load.java
│ └── LoadStatus.java
├── service
│ ├── DispatchManager.java
│ └── ReportService.java
├── util
│ └── InputHelper.java
└── Main.java

## How to Run

1. Clone the repository
2. Open the project in IntelliJ IDEA
3. Run `Main.java`

## Future Improvements

- Data persistence (save/load from files)
- Database integration (PostgreSQL or MySQL)
- REST API with Spring Boot
- Web dashboard for dispatch management
- Route optimization features
