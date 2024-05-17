package org.example.service;

import org.example.bom.Country;
import org.example.converter.Converter;
import org.example.dto.db.CountryDTO;
import org.example.repository.CountryRepository;
import org.example.service.CountryService.CountryService;
import org.example.service.CountryService.CountryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CountryServiceTest {

    @Mock
    private Converter<CountryDTO, Country> countryConverter;

    @Mock
    private CountryRepository countryRepository;

    private CountryService countryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        countryService = new CountryServiceImpl(countryConverter, countryRepository);
    }

    @Test
    void findById() {
        Country country = new Country(1L, "country");
        CountryDTO countryDTO = new CountryDTO(1L, "country");

        when(countryRepository.findById(country.getId())).thenReturn(Optional.of(countryDTO));
        when(countryConverter.fromDTO(countryDTO)).thenReturn(country);

        Country actualCountry = countryService.findById(country.getId());

        assertEquals(country, actualCountry);

        verify(countryRepository).findById(country.getId());
        verify(countryConverter).fromDTO(countryDTO);
    }

    @Test
    void findById_Null() {
        Country country = new Country(1L, "country");

        when(countryRepository.findById(country.getId())).thenReturn(Optional.empty());
        when(countryConverter.fromDTO(null)).thenReturn(null);

        Country actualCountry = countryService.findById(country.getId());

        assertNull(actualCountry);

        verify(countryRepository).findById(country.getId());
        verify(countryConverter).fromDTO(null);
    }
}