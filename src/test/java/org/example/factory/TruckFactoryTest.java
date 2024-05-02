package org.example.factory;

import org.example.bom.Color;
import org.example.bom.Model;
import org.example.bom.Truck;
import org.example.bom.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class TruckFactoryTest {

    private TruckFactory truckFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        truckFactory = new TruckFactory();
    }

    @Test
    void createVehicle() {
        Model model = new Model();
        Color color = new Color();
        Vehicle vehicle = truckFactory.createVehicle(1L, 5000, model, color, 6000, 2022);

        assertNotNull(vehicle);
        assertInstanceOf(Truck.class, vehicle);
        assertEquals(1L, vehicle.getId());
        assertEquals(5000, vehicle.getAmount());
        assertEquals(model, vehicle.getModel());
        assertEquals(color, vehicle.getColor());
        assertEquals(6000, vehicle.getPrice());
        assertEquals(2022, vehicle.getYear());
    }
}