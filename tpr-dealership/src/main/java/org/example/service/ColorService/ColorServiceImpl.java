package org.example.service.ColorService;

import lombok.RequiredArgsConstructor;
import org.example.bom.Color;
import org.example.converter.Converter;
import org.example.dto.db.ColorDTO;
import org.example.repository.ColorRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;

    private final Converter<ColorDTO, Color> colorConverter;

    @Override
    public Color save(Color color) {
        ColorDTO colorDTO = colorConverter.toDTO(color);
        ColorDTO savedColorDTO = colorRepository.save(colorDTO);
        return colorConverter.fromDTO(savedColorDTO);
    }

    @Override
    public Color findById(Long id) {
        ColorDTO colorDTO = colorRepository.findById(id).orElse(null);
        return colorConverter.fromDTO(colorDTO);
    }

    @Override
    public Color getOrCreateByName(Color color) {
        ColorDTO colorDTO;
        colorDTO = colorRepository.findByName(color.getName());

        if (colorDTO == null) {
            colorDTO = colorConverter.toDTO(color);
            colorDTO = colorRepository.save(colorDTO);
        }

        return colorConverter.fromDTO(colorDTO);
    }
}
