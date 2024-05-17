package org.example.printer;

import org.example.bom.Truck;
import org.example.bom.Vehicle;

public class TruckPrinter implements VehiclePrinter {

    @Override
    public String print(Vehicle vehicle) {
        Truck truck = (Truck) vehicle;
        return "Vehicle ID: " + truck.getId() + "\n" +
                "Amount: " + truck.getAmount() + "\n" +
                "Model: " + truck.getModel().getName() + "\n" +
                "Color: " + truck.getColor().getName() + "\n" +
                "Type: Truck\n" +
                "Price: " + truck.getPrice() + "\n" +
                "Year: " + truck.getYear() + "\n" +
                "Credit: " + truck.calculateCredit() + "\n";
    }
}
