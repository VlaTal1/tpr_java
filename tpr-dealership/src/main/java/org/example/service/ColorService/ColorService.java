package org.example.service.ColorService;

import org.example.bom.Color;

public interface ColorService {

    Color save(Color color);

    Color findById(Long id);
}
