package org.example.service;

import org.example.bom.Color;
import org.example.converter.Converter;
import org.example.dto.db.ColorDTO;
import org.example.repository.ColorRepository;
import org.example.service.ColorService.ColorService;
import org.example.service.ColorService.ColorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ColorServiceTest {

    @Mock
    private ColorRepository colorRepository;

    @Mock
    private Converter<ColorDTO, Color> colorConverter;

    private ColorService colorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        colorService = new ColorServiceImpl(colorRepository, colorConverter);
    }

    @Test
    void save() {
        Color color = new Color(1L, "Red");
        ColorDTO colorDTO = new ColorDTO(1L, "Red");

        when(colorConverter.toDTO(color)).thenReturn(colorDTO);
        when(colorRepository.save(colorDTO)).thenReturn(colorDTO);
        when(colorConverter.fromDTO(colorDTO)).thenReturn(color);

        Color actualColor = colorService.save(color);

        assertEquals(color, actualColor);

        verify(colorConverter).toDTO(color);
        verify(colorRepository).save(colorDTO);
        verify(colorConverter).fromDTO(colorDTO);
    }

    @Test
    void findById() {
        Color color = new Color(1L, "Red");
        ColorDTO colorDTO = new ColorDTO(1L, "Red");

        when(colorRepository.findById(color.getId())).thenReturn(Optional.of(colorDTO));
        when(colorConverter.fromDTO(colorDTO)).thenReturn(color);

        Color actualColor = colorService.findById(color.getId());

        assertEquals(color, actualColor);

        verify(colorRepository).findById(color.getId());
        verify(colorConverter).fromDTO(colorDTO);
    }

    @Test
    void findById_Null() {
        Color color = new Color(1L, "Red");

        when(colorRepository.findById(color.getId())).thenReturn(Optional.empty());
        when(colorConverter.fromDTO(null)).thenReturn(null);

        Color actualColor = colorService.findById(color.getId());

        assertNull(actualColor);

        verify(colorRepository).findById(color.getId());
        verify(colorConverter).fromDTO(null);
    }
}