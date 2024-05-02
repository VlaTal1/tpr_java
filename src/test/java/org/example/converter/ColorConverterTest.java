package org.example.converter;

import org.example.bom.Color;
import org.example.dto.db.ColorDTO;
import org.example.service.DealService.DealServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class ColorConverterTest {

    private Converter<ColorDTO, Color> colorConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        colorConverter = new ColorConverter();
    }

    @Test
    void fromDTO() {
        ColorDTO colorDTO = new ColorDTO(1L, "Red");
        Color actualColor = colorConverter.fromDTO(colorDTO);

        assertNotNull(actualColor);
        assertEquals(1L, actualColor.getId());
        assertEquals("Red", actualColor.getName());
    }

    @Test
    void fromDTO_Null() {
        Color actualColor = colorConverter.fromDTO(null);
        assertNull(actualColor);
    }

    @Test
    void toDTO() {
        Color color = new Color(1L, "Red");
        ColorDTO actualColor = colorConverter.toDTO(color);

        assertNotNull(actualColor);
        assertEquals(1L, actualColor.getId());
        assertEquals("Red", actualColor.getName());
    }
}