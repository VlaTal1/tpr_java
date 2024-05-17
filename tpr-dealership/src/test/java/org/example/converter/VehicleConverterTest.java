package org.example.converter;

import org.example.bom.*;
import org.example.dto.db.ColorDTO;
import org.example.dto.db.ModelDTO;
import org.example.dto.db.VehicleDTO;
import org.example.factory.PassengerCarFactory;
import org.example.factory.VehicleFactory;
import org.example.service.DealService.DealServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class VehicleConverterTest {

    @Mock
    private Converter<ModelDTO, Model> modelConverter;

    @Mock
    private Converter<ColorDTO, Color> colorConverter;

    private Converter<VehicleDTO, Vehicle> vehicleConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vehicleConverter = new VehicleConverter(modelConverter, colorConverter);
    }

    @Test
    void fromDTO() {
        ModelDTO modelDTO = new ModelDTO();
        ColorDTO colorDTO = new ColorDTO();
        VehicleDTO vehicleDTO = new VehicleDTO(1L, 5000, modelDTO, Type.PASSENGER_CAR.toString(), colorDTO, 6000F, 2022);

        Model model = new Model();
        Color color = new Color();
        when(modelConverter.fromDTO(modelDTO)).thenReturn(model);
        when(colorConverter.fromDTO(colorDTO)).thenReturn(color);

        Vehicle actualVehicle = vehicleConverter.fromDTO(vehicleDTO);

        assertNotNull(actualVehicle);
        assertEquals(1L, actualVehicle.getId());
        assertEquals(5000, actualVehicle.getAmount());
        assertEquals(model, actualVehicle.getModel());
        assertEquals(color, actualVehicle.getColor());
        assertEquals(6000, actualVehicle.getPrice());
        assertEquals(2022, actualVehicle.getYear());
        assertInstanceOf(PassengerCar.class, actualVehicle);
    }

    @Test
    void toDTO() {
        Model model = new Model();
        Color color = new Color();
        Vehicle vehicle = new PassengerCar(1L, 5000, model, color, 6000, 2022);

        ModelDTO modelDTO = new ModelDTO();
        ColorDTO colorDTO = new ColorDTO();
        when(modelConverter.toDTO(model)).thenReturn(modelDTO);
        when(colorConverter.toDTO(color)).thenReturn(colorDTO);

        VehicleDTO actualVehicle = vehicleConverter.toDTO(vehicle);

        assertNotNull(actualVehicle);
        assertEquals(1L, actualVehicle.getId());
        assertEquals(5000, actualVehicle.getAmount());
        assertEquals(modelDTO, actualVehicle.getModel());
        assertEquals(colorDTO, actualVehicle.getColor());
        assertEquals(6000, actualVehicle.getPrice());
        assertEquals(2022, actualVehicle.getYear());
        assertEquals(Type.PASSENGER_CAR.toString(), actualVehicle.getType());
    }
}