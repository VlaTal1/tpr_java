package org.example.service.ManufacturerService;

import org.example.bom.Manufacturer;

public interface ManufacturerService {

    Manufacturer findById(Long id);

    Manufacturer getOrCreateByName(Manufacturer manufacturer);
}
