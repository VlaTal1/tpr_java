package org.example.service;

import org.example.bom.Motorcycle;
import org.example.bom.PassengerCar;
import org.example.bom.Truck;
import org.example.bom.Vehicle;
import org.example.printer.MotorcyclePrinter;
import org.example.printer.PassengerCarPrinter;
import org.example.printer.TruckPrinter;
import org.example.printer.VehiclePrinter;

import java.util.HashMap;
import java.util.Map;

public class VehicleService {

    private final Map<Class<? extends Vehicle>, VehiclePrinter> printers;

    public VehicleService() {
        this.printers = new HashMap<>();
        // Додамо принтери для кожного типу транспортного засобу
        printers.put(PassengerCar.class, new PassengerCarPrinter());
        printers.put(Truck.class, new TruckPrinter());
        printers.put(Motorcycle.class, new MotorcyclePrinter());
    }

    public String printVehicle(Vehicle vehicle) {
        VehiclePrinter printer = printers.get(vehicle.getClass());
        if (printer != null) {
            return printer.print(vehicle);
        } else {
            return "No printer found";
        }
    }
}

//public class VehicleService {
//
//    public String printVehicle(Vehicle vehicle) {
//        if (vehicle instanceof PassengerCar) {
//            PassengerCarPrinter printer = new PassengerCarPrinter();
//            return printer.print(vehicle);
//        } else if (vehicle instanceof Truck) {
//            TruckPrinter printer = new TruckPrinter();
//            return printer.print(vehicle);
//        } else if (vehicle instanceof Motorcycle) {
//            MotorcyclePrinter printer = new MotorcyclePrinter();
//            return printer.print(vehicle);
//        } else {
//            return "No printer found for vehicle type: " + vehicle.getClass().getSimpleName();
//        }
//    }
//}
