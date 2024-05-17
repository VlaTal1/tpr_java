package org.example.bom;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VehicleTest {

    private final Country country = new Country(1L, "country");

    private final Manufacturer manufacturer = new Manufacturer(1L, "manufacturer", country);

    private final Model model = new Model(1L, "ModelA", manufacturer);

    private final Color color = new Color(1L, "Red");

    @Test
    public void testMotorcycleCalculateCredit() {
        Motorcycle motorcycle = new Motorcycle(1L, 5000, model, color, 6000, 2022);
        assertEquals(188, motorcycle.calculateCredit());
    }

    @Test
    public void testPassengerCarCalculateCredit() {
        PassengerCar passengerCar = new PassengerCar(1L, 5000, model, color, 6000, 2022);
        assertEquals(91, passengerCar.calculateCredit());
    }

    @Test
    public void testTruckCalculateCredit() {
        Truck truck = new Truck(1L, 5000, model, color, 6000, 2022);
        assertEquals(15, truck.calculateCredit());
    }
}

