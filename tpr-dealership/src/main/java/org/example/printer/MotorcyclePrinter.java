package org.example.printer;

import org.example.bom.Motorcycle;
import org.example.bom.Vehicle;

public class MotorcyclePrinter implements VehiclePrinter {

    @Override
    public String print(Vehicle vehicle) {
        Motorcycle motorcycle = (Motorcycle) vehicle;
        return "Vehicle ID: " + motorcycle.getId() + "\n" +
                "Amount: " + motorcycle.getAmount() + "\n" +
                "Model: " + motorcycle.getModel().getName() + "\n" +
                "Color: " + motorcycle.getColor().getName() + "\n" +
                "Type: Motorcycle\n" +
                "Price: " + motorcycle.getPrice() + "\n" +
                "Year: " + motorcycle.getYear() + "\n" +
                "Credit: " + motorcycle.calculateCredit() + "\n";
    }
}
