package org.example.service;

import org.example.bom.*;
import org.example.converter.Converter;
import org.example.dto.db.*;
import org.example.exception.NotFoundException;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class VehicleServiceTest {

    private Country country;

    private Manufacturer manufacturer;

    private Model model;

    private Color color;

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

        country = new Country(1L, "country");
        manufacturer = new Manufacturer(1L, "manufacturer", country);
        model = new Model(1L, "ModelA", manufacturer);
        color = new Color(1L, "Red");
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

    @Test
    public void saveTest() throws NotFoundException {
        Country country = new Country(1L, "country");
        Manufacturer manufacturer = new Manufacturer(1L, "manufacturer", country);
        Model model = new Model(1L, "ModelA", manufacturer);
        Color color = new Color(1L, "Red");
        Vehicle vehicle = new PassengerCar(1L, 5000, model, color, 6000, 2022);

        CountryDTO countryDTO = new CountryDTO(1L, "country");
        ManufacturerDTO manufacturerDTO = new ManufacturerDTO(1L, "manufacturer", countryDTO);
        ModelDTO modelDTO = new ModelDTO(1L, "ModelA", manufacturerDTO);
        ColorDTO colorDTO = new ColorDTO(1L, "Red");
        VehicleDTO vehicleDTO = new VehicleDTO(1L, 5000, modelDTO, Type.PASSENGER_CAR.toString(), colorDTO, 6000F, 2022);

        when(colorService.findById(color.getId())).thenReturn(color);
        when(modelService.findById(model.getId())).thenReturn(model);
        when(manufacturerService.findById(manufacturer.getId())).thenReturn(manufacturer);
        when(countryService.findById(country.getId())).thenReturn(country);

        when(vehicleConverter.toDTO(vehicle)).thenReturn(vehicleDTO);
        when(vehicleRepository.save(vehicleDTO)).thenReturn(vehicleDTO);
        when(vehicleConverter.fromDTO(vehicleDTO)).thenReturn(vehicle);

        Vehicle savedVehicle = vehicleService.save(vehicle);
        assertEquals(vehicle, savedVehicle);
    }

    @Test
    public void updateTest() throws NotFoundException {
        Country country = new Country(1L, "country");
        Manufacturer manufacturer = new Manufacturer(1L, "manufacturer", country);
        Model model = new Model(1L, "ModelA", manufacturer);
        Color color = new Color(1L, "Red");
        Vehicle vehicle = new PassengerCar(1L, 5000, model, color, 6000, 2022);

        CountryDTO countryDTO = new CountryDTO(1L, "country");
        ManufacturerDTO manufacturerDTO = new ManufacturerDTO(1L, "manufacturer", countryDTO);
        ModelDTO modelDTO = new ModelDTO(1L, "ModelA", manufacturerDTO);
        ColorDTO colorDTO = new ColorDTO(1L, "Red");
        VehicleDTO vehicleDTO = new VehicleDTO(1L, 5000, modelDTO, Type.PASSENGER_CAR.toString(), colorDTO, 6000F, 2022);

        when(vehicleConverter.toDTO(vehicle)).thenReturn(vehicleDTO);
        when(vehicleRepository.save(vehicleDTO)).thenReturn(vehicleDTO);
        when(vehicleConverter.fromDTO(vehicleDTO)).thenReturn(vehicle);

        Vehicle updatedVehicle = vehicleService.update(vehicle);
        assertEquals(vehicle, updatedVehicle);
    }

    @Test
    public void findByIdTest() throws NotFoundException {
        Country country = new Country(1L, "country");
        Manufacturer manufacturer = new Manufacturer(1L, "manufacturer", country);
        Model model = new Model(1L, "ModelA", manufacturer);
        Color color = new Color(1L, "Red");
        Vehicle vehicle = new PassengerCar(1L, 5000, model, color, 6000, 2022);

        CountryDTO countryDTO = new CountryDTO(1L, "country");
        ManufacturerDTO manufacturerDTO = new ManufacturerDTO(1L, "manufacturer", countryDTO);
        ModelDTO modelDTO = new ModelDTO(1L, "ModelA", manufacturerDTO);
        ColorDTO colorDTO = new ColorDTO(1L, "Red");
        VehicleDTO vehicleDTO = new VehicleDTO(1L, 5000, modelDTO, Type.PASSENGER_CAR.toString(), colorDTO, 6000F, 2022);

        when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.of(vehicleDTO));
        when(vehicleConverter.fromDTO(vehicleDTO)).thenReturn(vehicle);

        Vehicle foundVehicle = vehicleService.findById(vehicle.getId());
        assertEquals(vehicle, foundVehicle);
    }

    @Test
    public void findByIdTest_NotFound() {
        Long id = 1L;

        when(vehicleRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> vehicleService.findById(id));
    }

    @Test
    public void isAvailableTest_True() throws NotFoundException {
        Country country = new Country(1L, "country");
        Manufacturer manufacturer = new Manufacturer(1L, "manufacturer", country);
        Model model = new Model(1L, "ModelA", manufacturer);
        Color color = new Color(1L, "Red");
        Vehicle vehicle = new PassengerCar(1L, 5000, model, color, 6000, 2022);

        CountryDTO countryDTO = new CountryDTO(1L, "country");
        ManufacturerDTO manufacturerDTO = new ManufacturerDTO(1L, "manufacturer", countryDTO);
        ModelDTO modelDTO = new ModelDTO(1L, "ModelA", manufacturerDTO);
        ColorDTO colorDTO = new ColorDTO(1L, "Red");
        VehicleDTO vehicleDTO = new VehicleDTO(1L, 5000, modelDTO, Type.PASSENGER_CAR.toString(), colorDTO, 6000F, 2022);

        when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.of(vehicleDTO));

        boolean isAvailable = vehicleService.isAvailable(vehicle.getId());
        assertTrue(isAvailable);
    }

    @Test
    public void isAvailableTest_False() throws NotFoundException {
        Country country = new Country(1L, "country");
        Manufacturer manufacturer = new Manufacturer(1L, "manufacturer", country);
        Model model = new Model(1L, "ModelA", manufacturer);
        Color color = new Color(1L, "Red");
        Vehicle vehicle = new PassengerCar(1L, 0, model, color, 6000, 2022);

        CountryDTO countryDTO = new CountryDTO(1L, "country");
        ManufacturerDTO manufacturerDTO = new ManufacturerDTO(1L, "manufacturer", countryDTO);
        ModelDTO modelDTO = new ModelDTO(1L, "ModelA", manufacturerDTO);
        ColorDTO colorDTO = new ColorDTO(1L, "Red");
        VehicleDTO vehicleDTO = new VehicleDTO(1L, 0, modelDTO, Type.PASSENGER_CAR.toString(), colorDTO, 6000F, 2022);

        when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.of(vehicleDTO));

        boolean isAvailable = vehicleService.isAvailable(vehicle.getId());
        assertFalse(isAvailable);
    }
}

