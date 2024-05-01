package org.example.service;

import org.example.bom.*;
import org.example.converter.Converter;
import org.example.dto.db.*;
import org.example.repository.DealRepository;
import org.example.service.DealService.DealService;
import org.example.service.DealService.DealServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DealServiceTest {

    @Mock
    private Converter<DealDTO, Deal> dealConverter;

    @Mock
    private DealRepository dealRepository;

    private DealService dealService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dealService = new DealServiceImpl(dealConverter, dealRepository);
    }

    @Test
    void save() {
        Country country = new Country(1L, "country");
        Manufacturer manufacturer = new Manufacturer(1L, "manufacturer", country);
        Model model = new Model(1L, "ModelA", manufacturer);
        Color color = new Color(1L, "Red");
        Vehicle vehicle = new PassengerCar(1L, 5000, model, color, 6000, 2022);
        Client client = new Client(1L, "John", "Address", "+380984785740", "UA023948274", 3);
        Employee employee = new Employee(1L, "Alex", "+38097847567", "Sales manages");
        Deal deal = new Deal(1L, vehicle, client, employee, Timestamp.valueOf(LocalDateTime.now()), 10000F);

        CountryDTO countryDTO = new CountryDTO(1L, "country");
        ManufacturerDTO manufacturerDTO = new ManufacturerDTO(1L, "manufacturer", countryDTO);
        ModelDTO modelDTO = new ModelDTO(1L, "ModelA", manufacturerDTO);
        ColorDTO colorDTO = new ColorDTO(1L, "Red");
        VehicleDTO vehicleDTO = new VehicleDTO(1L, 5000, modelDTO, Type.PASSENGER_CAR.toString(), colorDTO, 6000F, 2022);
        ClientDTO clientDTO = new ClientDTO(1L, "John", "Address", "+380984785740", "UA023948274", 3);
        EmployeeDTO employeeDTO = new EmployeeDTO(1L, "Alex", "+38097847567", "Sales manages");
        DealDTO dealDTO = new DealDTO(1L, vehicleDTO, clientDTO, employeeDTO, Timestamp.valueOf(LocalDateTime.now()), 10000F);

        when(dealConverter.toDTO(deal)).thenReturn(dealDTO);
        when(dealRepository.save(dealDTO)).thenReturn(dealDTO);
        when(dealConverter.fromDTO(dealDTO)).thenReturn(deal);

        Deal actualDeal = dealService.save(deal);

        assertEquals(deal, actualDeal);

        verify(dealConverter).toDTO(deal);
        verify(dealRepository).save(dealDTO);
        verify(dealConverter).fromDTO(dealDTO);
    }
}