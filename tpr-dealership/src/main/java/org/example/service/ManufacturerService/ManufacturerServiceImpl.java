package org.example.service.ManufacturerService;

import lombok.RequiredArgsConstructor;
import org.example.bom.Country;
import org.example.bom.Manufacturer;
import org.example.converter.Converter;
import org.example.converter.CountryConverter;
import org.example.dto.db.CountryDTO;
import org.example.dto.db.ManufacturerDTO;
import org.example.repository.ManufacturerRepository;
import org.example.service.CountryService.CountryService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManufacturerServiceImpl implements ManufacturerService {

    private final Converter<ManufacturerDTO, Manufacturer> manufacturerConverter;

    private final ManufacturerRepository manufacturerRepository;

    private final CountryService countryService;

    private final CountryConverter countryConverter;

    @Override
    public Manufacturer findById(Long id) {
        ManufacturerDTO manufacturerDTO = manufacturerRepository.findById(id).orElse(null);
        return manufacturerConverter.fromDTO(manufacturerDTO);
    }

    @Override
    public Manufacturer getOrCreateByName(Manufacturer manufacturer) {
        Country country = countryService.getOrCreateByName(manufacturer.getCountry());
        CountryDTO countryDTO = countryConverter.toDTO(country);

        ManufacturerDTO manufacturerDTO;
        manufacturerDTO = manufacturerRepository.findByNameAndCountry(manufacturer.getName(), countryDTO);

        manufacturer.setCountry(country);
        if (manufacturerDTO == null) {
            manufacturerDTO = manufacturerConverter.toDTO(manufacturer);
            manufacturerDTO = manufacturerRepository.save(manufacturerDTO);
        }
        
        return manufacturerConverter.fromDTO(manufacturerDTO);
    }
}
