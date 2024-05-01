package org.example.converter;

import org.example.bom.Country;
import org.example.dto.db.CountryDTO;
import org.example.service.DealService.DealServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class CountryConverterTest {

    private Converter<CountryDTO, Country> countryConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        countryConverter = new CountryConverter();
    }

    @Test
    void fromDTO() {
        CountryDTO countryDTO = new CountryDTO(1L, "country");

        Country actualCountry = countryConverter.fromDTO(countryDTO);

        assertNotNull(actualCountry);
        assertEquals(1L, actualCountry.getId());
        assertEquals("country", actualCountry.getName());
    }

    @Test
    void fromDTO_Null() {
        Country actualCountry = countryConverter.fromDTO(null);
        assertNull(actualCountry);
    }

    @Test
    void toDTO() {
        Country country = new Country(1L, "country");

        CountryDTO actualCountry = countryConverter.toDTO(country);

        assertNotNull(actualCountry);
        assertEquals(1L, actualCountry.getId());
        assertEquals("country", actualCountry.getName());
    }
}