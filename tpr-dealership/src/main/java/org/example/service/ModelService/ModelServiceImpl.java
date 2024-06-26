package org.example.service.ModelService;

import lombok.RequiredArgsConstructor;
import org.example.bom.Model;
import org.example.converter.Converter;
import org.example.dto.db.ModelDTO;
import org.example.repository.ModelRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {

    private final Converter<ModelDTO, Model> modelConverter;

    private final ModelRepository modelRepository;

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
}
