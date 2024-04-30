package org.example.service.ColorService;

import org.example.bom.Color;
import org.example.exception.NotFoundException;

public interface ColorService {

    Color save(Color color);

    Color findById(Long id) throws NotFoundException;
}
