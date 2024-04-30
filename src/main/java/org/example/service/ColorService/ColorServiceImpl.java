package org.example.service.ColorService;

import lombok.RequiredArgsConstructor;
import org.example.bom.Color;
import org.example.converter.Converter;
import org.example.dto.db.ColorDTO;
import org.example.exception.NotFoundException;
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
    public Color findById(Long id) throws NotFoundException {
        ColorDTO colorDTO = colorRepository.findById(id).orElse(null);
        if (colorDTO == null) {
            throw new NotFoundException(STR."Color with id \{id} not found");
        }
        return colorConverter.fromDTO(colorDTO);
    }
}
