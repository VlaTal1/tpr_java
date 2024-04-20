package org.example.printer;

import org.example.bom.*;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VehiclePrinterTest {

    private final Country country = new Country(1L, "country");

    private final Manufacturer manufacturer = new Manufacturer(1L, "manufacturer", country);

    private final Model model = new Model(1L, "ModelA", manufacturer);

    private final Color color = new Color(1L, "Red");


    @Test
    public void testMotorcyclePrinter() {

        // Создание объекта Motorcycle
        Motorcycle motorcycle = new Motorcycle(1L, 5000, model, color, 6000, 2022);

        // Создание объекта MotorcyclePrinter
        VehiclePrinter printer = new MotorcyclePrinter();

        // Проверка вывода
        String expectedOutput = "Vehicle ID: 1\n" +
                "Amount: 5000\n" +
                "Model: ModelA\n" +
                "Color: Red\n" +
                "Type: Motorcycle\n" +
                "Price: 6000.0\n" +
                "Year: 2022\n" +
                "Credit: 188\n";
        assertEquals(expectedOutput, printer.print(motorcycle));
    }

    @Test
    public void testPassengerCarPrinter() {
        // Создание объекта PassengerCar
        PassengerCar car = new PassengerCar(1L, 5000, model, color, 6000, 2022);

        // Создание объекта PassengerCarPrinter
        VehiclePrinter printer = new PassengerCarPrinter();

        // Проверка вывода
        String expectedOutput = "Vehicle ID: 1\n" +
                "Amount: 5000\n" +
                "Model: ModelA\n" +
                "Color: Red\n" +
                "Type: Passenger car\n" +
                "Price: 6000.0\n" +
                "Year: 2022\n" +
                "Credit: 91\n";
        assertEquals(expectedOutput, printer.print(car));
    }

    @Test
    public void testTruckPrinter() {
        // Создание объекта Truck
        Truck truck = new Truck(1L, 5000, model, color, 6000, 2022);

        // Создание объекта TruckPrinter
        VehiclePrinter printer = new TruckPrinter();

        // Проверка вывода
        String expectedOutput = "Vehicle ID: 1\n" +
                "Amount: 5000\n" +
                "Model: ModelA\n" +
                "Color: Red\n" +
                "Type: Truck\n" +
                "Price: 6000.0\n" +
                "Year: 2022\n" +
                "Credit: 15\n";
        assertEquals(expectedOutput, printer.print(truck));
    }
}
