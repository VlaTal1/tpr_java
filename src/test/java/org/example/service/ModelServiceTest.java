package org.example.service;

import org.example.bom.Country;
import org.example.bom.Manufacturer;
import org.example.bom.Model;
import org.example.converter.Converter;
import org.example.dto.db.CountryDTO;
import org.example.dto.db.ManufacturerDTO;
import org.example.dto.db.ModelDTO;
import org.example.repository.ModelRepository;
import org.example.service.ModelService.ModelService;
import org.example.service.ModelService.ModelServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ModelServiceTest {

    @Mock
    private Converter<ModelDTO, Model> modelConverter;

    @Mock
    private ModelRepository modelRepository;

    private ModelService modelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        modelService = new ModelServiceImpl(modelConverter, modelRepository);
    }

    @Test
    void save() {
        Country country = new Country(1L, "country");
        Manufacturer manufacturer = new Manufacturer(1L, "manufacturer", country);
        Model model = new Model(1L, "ModelA", manufacturer);
        CountryDTO countryDTO = new CountryDTO(1L, "country");
        ManufacturerDTO manufacturerDTO = new ManufacturerDTO(1L, "manufacturer", countryDTO);
        ModelDTO modelDTO = new ModelDTO(1L, "ModelA", manufacturerDTO);

        when(modelConverter.toDTO(model)).thenReturn(modelDTO);
        when(modelRepository.save(modelDTO)).thenReturn(modelDTO);
        when(modelConverter.fromDTO(modelDTO)).thenReturn(model);

        Model actualModel = modelService.save(model);

        assertEquals(model, actualModel);

        verify(modelConverter).toDTO(model);
        verify(modelRepository).save(modelDTO);
        verify(modelConverter).fromDTO(modelDTO);
    }

    @Test
    void findById() {
        Country country = new Country(1L, "country");
        Manufacturer manufacturer = new Manufacturer(1L, "manufacturer", country);
        Model model = new Model(1L, "ModelA", manufacturer);
        CountryDTO countryDTO = new CountryDTO(1L, "country");
        ManufacturerDTO manufacturerDTO = new ManufacturerDTO(1L, "manufacturer", countryDTO);
        ModelDTO modelDTO = new ModelDTO(1L, "ModelA", manufacturerDTO);

        when(modelRepository.findById(model.getId())).thenReturn(Optional.of(modelDTO));
        when(modelConverter.fromDTO(modelDTO)).thenReturn(model);

        Model actualModel = modelService.findById(model.getId());

        assertEquals(model, actualModel);

        verify(modelRepository).findById(model.getId());
        verify(modelConverter).fromDTO(modelDTO);
    }

    @Test
    void findById_Null() {
        Country country = new Country(1L, "country");
        Manufacturer manufacturer = new Manufacturer(1L, "manufacturer", country);
        Model model = new Model(1L, "ModelA", manufacturer);

        when(modelRepository.findById(model.getId())).thenReturn(Optional.empty());
        when(modelConverter.fromDTO(null)).thenReturn(null);

        Model actualModel = modelService.findById(model.getId());

        assertNull(actualModel);

        verify(modelRepository).findById(model.getId());
        verify(modelConverter).fromDTO(null);
    }
}