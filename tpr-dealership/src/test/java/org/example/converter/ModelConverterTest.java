package org.example.converter;

import org.example.bom.Manufacturer;
import org.example.bom.Model;
import org.example.dto.db.ManufacturerDTO;
import org.example.dto.db.ModelDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class ModelConverterTest {

    @Mock
    private Converter<ManufacturerDTO, Manufacturer> manufacturerConverter;

    private Converter<ModelDTO, Model> modelConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        modelConverter = new ModelConverter(manufacturerConverter);
    }

    @Test
    void fromDTO() {
        ManufacturerDTO manufacturerDTO = new ManufacturerDTO();
        ModelDTO modelDTO = new ModelDTO(1L, "ModelA", manufacturerDTO);

        Manufacturer manufacturer = new Manufacturer();
        when(manufacturerConverter.fromDTO(manufacturerDTO)).thenReturn(manufacturer);

        Model actualModel = modelConverter.fromDTO(modelDTO);

        assertEquals(1L, actualModel.getId());
        assertEquals("ModelA", actualModel.getName());
        assertEquals(manufacturer, actualModel.getManufacturer());
    }

    @Test
    void fromDTO_Null() {
        Model actualModel = modelConverter.fromDTO(null);
        assertNull(actualModel);
    }

    @Test
    void toDTO() {
        Manufacturer manufacturer = new Manufacturer();
        Model model = new Model(1L, "ModelA", manufacturer);

        ManufacturerDTO manufacturerDTO = new ManufacturerDTO();
        when(manufacturerConverter.toDTO(manufacturer)).thenReturn(manufacturerDTO);

        ModelDTO actualModel = modelConverter.toDTO(model);

        assertEquals(1L, actualModel.getId());
        assertEquals("ModelA", actualModel.getName());
        assertEquals(manufacturerDTO, actualModel.getManufacturer());
    }
}