package org.example.converter;

import org.example.bom.*;
import org.example.dto.web.VehicleRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class VehicleRequestConverterTest {

    private Converter<VehicleRequest, Vehicle> vehicleRequestConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vehicleRequestConverter = new VehicleRequestConverter();
    }

    @Test
    void fromDTO() {
        Model model = new Model();
        Color color = new Color();
        VehicleRequest vehicleDTO = new VehicleRequest(1L, 5000, model, color, 6000F, 2022, Type.PASSENGER_CAR, false);

        Vehicle actualVehicle = vehicleRequestConverter.fromDTO(vehicleDTO);

        assertNotNull(actualVehicle);
        assertEquals(1L, actualVehicle.getId());
        assertEquals(5000, actualVehicle.getAmount());
        assertEquals(model, actualVehicle.getModel());
        assertEquals(color, actualVehicle.getColor());
        assertEquals(6000F, actualVehicle.getPrice());
        assertEquals(2022, actualVehicle.getYear());
        assertFalse(actualVehicle.isUsed());
        assertInstanceOf(PassengerCar.class, actualVehicle);
    }

    @Test
    void toDTO() {
        Vehicle vehicle = new PassengerCar();
        assertThrows(UnsupportedOperationException.class, () -> vehicleRequestConverter.toDTO(vehicle));
    }
}
