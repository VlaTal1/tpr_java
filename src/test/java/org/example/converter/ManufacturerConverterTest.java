package org.example.converter;

import org.example.bom.Country;
import org.example.bom.Manufacturer;
import org.example.dto.db.CountryDTO;
import org.example.dto.db.ManufacturerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class ManufacturerConverterTest {

    @Mock
    private Converter<CountryDTO, Country> countryConverter;

    private Converter<ManufacturerDTO, Manufacturer> manufacturerConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        manufacturerConverter = new ManufacturerConverter(countryConverter);
    }

    @Test
    void fromDTO() {
        CountryDTO countryDTO = new CountryDTO();
        ManufacturerDTO manufacturerDTO = new ManufacturerDTO(1L, "manufacturer", countryDTO);

        Country country = new Country();
        when(countryConverter.fromDTO(countryDTO)).thenReturn(country);

        Manufacturer actualManufacturer = manufacturerConverter.fromDTO(manufacturerDTO);

        assertEquals(1L, actualManufacturer.getId());
        assertEquals("manufacturer", actualManufacturer.getName());
        assertEquals(country, actualManufacturer.getCountry());
    }

    @Test
    void fromDTO_Null() {
        Manufacturer actualManufacturer = manufacturerConverter.fromDTO(null);
        assertNull(actualManufacturer);
    }

    @Test
    void toDTO() {
        Country country = new Country();
        Manufacturer manufacturer = new Manufacturer(1L, "manufacturer", country);

        CountryDTO countryDTO = new CountryDTO();
        when(countryConverter.toDTO(country)).thenReturn(countryDTO);

        ManufacturerDTO actualManufacturer = manufacturerConverter.toDTO(manufacturer);

        assertEquals(1L, actualManufacturer.getId());
        assertEquals("manufacturer", actualManufacturer.getName());
        assertEquals(countryDTO, actualManufacturer.getCountry());
    }
}