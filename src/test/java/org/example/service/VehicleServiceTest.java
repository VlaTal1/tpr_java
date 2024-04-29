package org.example.service;

import org.example.bom.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VehicleServiceTest {

    private final Country country = new Country(1L, "country");

    private final Manufacturer manufacturer = new Manufacturer(1L, "manufacturer", country);

    private final Model model = new Model(1L, "ModelA", manufacturer);

    private final Color color = new Color(1L, "Red");

    @Test
    public void testPrintVehiclePassengerCar() {
        VehicleService service = new VehicleService();
        Vehicle vehicle = new PassengerCar(1L, 5000, model, color, 6000, 2022);
        String expectedOutput = "Vehicle ID: 1\n" +
                "Amount: 5000\n" +
                "Model: ModelA\n" +
                "Color: Red\n" +
                "Type: Passenger car\n" +
                "Price: 6000.0\n" +
                "Year: 2022\n" +
                "Credit: 91\n";
        assertEquals(expectedOutput, service.printVehicle(vehicle));
    }

    @Test
    public void testPrintVehicleTruck() {
        VehicleService service = new VehicleService();
        Vehicle vehicle = new Truck(1L, 5000, model, color, 6000, 2022);
        String expectedOutput = "Vehicle ID: 1\n" +
                "Amount: 5000\n" +
                "Model: ModelA\n" +
                "Color: Red\n" +
                "Type: Truck\n" +
                "Price: 6000.0\n" +
                "Year: 2022\n" +
                "Credit: 15\n";
        assertEquals(expectedOutput, service.printVehicle(vehicle));
    }

    @Test
    public void testPrintVehicleMotorcycle() {
        VehicleService service = new VehicleService();
        Vehicle vehicle = new Motorcycle(1L, 5000, model, color, 6000, 2022);
        String expectedOutput = "Vehicle ID: 1\n" +
                "Amount: 5000\n" +
                "Model: ModelA\n" +
                "Color: Red\n" +
                "Type: Motorcycle\n" +
                "Price: 6000.0\n" +
                "Year: 2022\n" +
                "Credit: 188\n";
        assertEquals(expectedOutput, service.printVehicle(vehicle));
    }

    @Test
    public void testPrintVehicleNotFound() {
        VehicleService service = new VehicleService();
        Vehicle vehicle = new Vehicle(1L, 5000, model, color, 6000, 2022) {
            @Override
            public int calculateCredit() {
                return 0;
            }
        };
        String expectedOutput = "No printer found";
        assertEquals(expectedOutput, service.printVehicle(vehicle));
    }
}

