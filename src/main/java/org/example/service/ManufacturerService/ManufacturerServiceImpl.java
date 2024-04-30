package org.example.service.ManufacturerService;

import lombok.RequiredArgsConstructor;
import org.example.bom.Manufacturer;
import org.example.converter.Converter;
import org.example.dto.db.ManufacturerDTO;
import org.example.repository.ManufacturerRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManufacturerServiceImpl implements ManufacturerService {

    private final Converter<ManufacturerDTO, Manufacturer> manufacturerConverter;

    private final ManufacturerRepository manufacturerRepository;

    @Override
    public Manufacturer findById(Long id) {
        ManufacturerDTO manufacturerDTO = manufacturerRepository.findById(id).orElse(null);
        return manufacturerConverter.fromDTO(manufacturerDTO);
    }
}
