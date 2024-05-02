package org.example.service;

import org.example.bom.Country;
import org.example.bom.Manufacturer;
import org.example.converter.Converter;
import org.example.dto.db.CountryDTO;
import org.example.dto.db.ManufacturerDTO;
import org.example.repository.ManufacturerRepository;
import org.example.service.ManufacturerService.ManufacturerService;
import org.example.service.ManufacturerService.ManufacturerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ManufacturerServiceTest {

    @Mock
    private Converter<ManufacturerDTO, Manufacturer> manufacturerConverter;

    @Mock
    private ManufacturerRepository manufacturerRepository;

    private ManufacturerService manufacturerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        manufacturerService = new ManufacturerServiceImpl(manufacturerConverter, manufacturerRepository);
    }

    @Test
    void findById() {
        Country country = new Country(1L, "country");
        Manufacturer manufacturer = new Manufacturer(1L, "manufacturer", country);
        CountryDTO countryDTO = new CountryDTO(1L, "country");
        ManufacturerDTO manufacturerDTO = new ManufacturerDTO(1L, "manufacturer", countryDTO);

        when(manufacturerRepository.findById(manufacturer.getId())).thenReturn(Optional.of(manufacturerDTO));
        when(manufacturerConverter.fromDTO(manufacturerDTO)).thenReturn(manufacturer);

        Manufacturer actualManufacturer = manufacturerService.findById(manufacturer.getId());

        assertEquals(manufacturer, actualManufacturer);

        verify(manufacturerRepository).findById(manufacturer.getId());
        verify(manufacturerConverter).fromDTO(manufacturerDTO);
    }

    @Test
    void findById_Null() {
        Country country = new Country(1L, "country");
        Manufacturer manufacturer = new Manufacturer(1L, "manufacturer", country);

        when(manufacturerRepository.findById(manufacturer.getId())).thenReturn(Optional.empty());
        when(manufacturerConverter.fromDTO(null)).thenReturn(null);

        Manufacturer actualManufacturer = manufacturerService.findById(manufacturer.getId());

        assertNull(actualManufacturer);

        verify(manufacturerRepository).findById(manufacturer.getId());
    }
}