package org.example.service;

import org.example.bom.*;
import org.example.converter.Converter;
import org.example.dto.db.VehicleDTO;
import org.example.repository.VehicleRepository;
import org.example.service.ColorService.ColorService;
import org.example.service.CountryService.CountryService;
import org.example.service.ManufacturerService.ManufacturerService;
import org.example.service.ModelService.ModelService;
import org.example.service.VehicleService.VehicleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VehicleServiceTest {

    private final Country country = new Country(1L, "country");

    private final Manufacturer manufacturer = new Manufacturer(1L, "manufacturer", country);

    private final Model model = new Model(1L, "ModelA", manufacturer);

    private final Color color = new Color(1L, "Red");

    @Mock
    private Converter<VehicleDTO, Vehicle> vehicleConverter;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private ColorService colorService;

    @Mock
    private ModelService modelService;

    @Mock
    private ManufacturerService manufacturerService;

    @Mock
    private CountryService countryService;

    private VehicleServiceImpl vehicleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vehicleService = new VehicleServiceImpl(vehicleConverter, vehicleRepository, colorService, modelService, manufacturerService, countryService);
    }

    @Test
    public void testPrintVehiclePassengerCar() {
        Vehicle vehicle = new PassengerCar(1L, 5000, model, color, 6000, 2022);
        String expectedOutput = "Vehicle ID: 1\n" +
                "Amount: 5000\n" +
                "Model: ModelA\n" +
                "Color: Red\n" +
                "Type: Passenger car\n" +
                "Price: 6000.0\n" +
                "Year: 2022\n" +
                "Credit: 91\n";
        assertEquals(expectedOutput, vehicleService.printVehicle(vehicle));
    }

    @Test
    public void testPrintVehicleTruck() {
        Vehicle vehicle = new Truck(1L, 5000, model, color, 6000, 2022);
        String expectedOutput = "Vehicle ID: 1\n" +
                "Amount: 5000\n" +
                "Model: ModelA\n" +
                "Color: Red\n" +
                "Type: Truck\n" +
                "Price: 6000.0\n" +
                "Year: 2022\n" +
                "Credit: 15\n";
        assertEquals(expectedOutput, vehicleService.printVehicle(vehicle));
    }

    @Test
    public void testPrintVehicleMotorcycle() {
        Vehicle vehicle = new Motorcycle(1L, 5000, model, color, 6000, 2022);
        String expectedOutput = "Vehicle ID: 1\n" +
                "Amount: 5000\n" +
                "Model: ModelA\n" +
                "Color: Red\n" +
                "Type: Motorcycle\n" +
                "Price: 6000.0\n" +
                "Year: 2022\n" +
                "Credit: 188\n";
        assertEquals(expectedOutput, vehicleService.printVehicle(vehicle));
    }

    @Test
    public void testPrintVehicleNotFound() {
        Vehicle vehicle = new Vehicle(1L, 5000, model, color, 6000, 2022) {
            @Override
            public int calculateCredit() {
                return 0;
            }
        };
        String expectedOutput = "No printer found";
        assertEquals(expectedOutput, vehicleService.printVehicle(vehicle));
    }
}

