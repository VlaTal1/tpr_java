package org.example.service.ModelService;

import lombok.RequiredArgsConstructor;
import org.example.bom.Manufacturer;
import org.example.bom.Model;
import org.example.converter.Converter;
import org.example.converter.ManufacturerConverter;
import org.example.dto.db.ManufacturerDTO;
import org.example.dto.db.ModelDTO;
import org.example.repository.ModelRepository;
import org.example.service.ManufacturerService.ManufacturerService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {

    private final Converter<ModelDTO, Model> modelConverter;

    private final ModelRepository modelRepository;

    private final ManufacturerService manufacturerService;

    private final ManufacturerConverter manufacturerConverter;

    @Override
    public Model save(Model model) {
        ModelDTO modelDTO = modelConverter.toDTO(model);
        ModelDTO savedModelDTO = modelRepository.save(modelDTO);
        return modelConverter.fromDTO(savedModelDTO);
    }

    @Override
    public Model findById(Long id) {
        ModelDTO modelDTO = modelRepository.findById(id).orElse(null);
        return modelConverter.fromDTO(modelDTO);
    }

    @Override
    public Model getOrCreateByName(Model model) {
        Manufacturer manufacturer = manufacturerService.getOrCreateByName(model.getManufacturer());
        ManufacturerDTO manufacturerDTO = manufacturerConverter.toDTO(manufacturer);

        ModelDTO modelDTO;
        modelDTO = modelRepository.findByNameAndManufacturer(model.getName(), manufacturerDTO);

        model.setManufacturer(manufacturer);
        if (modelDTO == null) {
            modelDTO = modelConverter.toDTO(model);
            modelDTO = modelRepository.save(modelDTO);
        }

        return modelConverter.fromDTO(modelDTO);
    }
}
