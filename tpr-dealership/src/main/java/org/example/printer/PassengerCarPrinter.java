package org.example.printer;

import org.example.bom.PassengerCar;
import org.example.bom.Vehicle;

public class PassengerCarPrinter implements VehiclePrinter {

    @Override
    public String print(Vehicle vehicle) {
        PassengerCar car = (PassengerCar) vehicle;
        return "Vehicle ID: " + car.getId() + "\n" +
                "Amount: " + car.getAmount() + "\n" +
                "Model: " + car.getModel().getName() + "\n" +
                "Color: " + car.getColor().getName() + "\n" +
                "Type: Passenger car\n" +
                "Price: " + car.getPrice() + "\n" +
                "Year: " + car.getYear() + "\n" +
                "Credit: " + car.calculateCredit() + "\n";
    }
}
