package org.example.converter;

import org.example.bom.Color;
import org.example.dto.db.ColorDTO;
import org.springframework.stereotype.Component;

@Component
public class ColorConverter implements Converter<ColorDTO, Color> {

    @Override
    public Color fromDTO(ColorDTO DTO) {
        return Color.builder()
                .id(DTO.getId())
                .name(DTO.getName())
                .build();
    }

    @Override
    public ColorDTO toDTO(Color BOM) {
        return ColorDTO.builder()
                .id(BOM.getId())
                .name(BOM.getName())
                .build();
    }
}
