package org.example.converter;

import lombok.RequiredArgsConstructor;
import org.example.bom.Country;
import org.example.bom.Manufacturer;
import org.example.dto.db.CountryDTO;
import org.example.dto.db.ManufacturerDTO;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ManufacturerConverter implements Converter<ManufacturerDTO, Manufacturer> {

    private final Converter<CountryDTO, Country> countryConverter;

    @Override
    public Manufacturer fromDTO(ManufacturerDTO DTO) {
        if (DTO == null) return null;
        return Manufacturer.builder()
                .id(DTO.getId())
                .name(DTO.getName())
                .country(countryConverter.fromDTO(DTO.getCountry()))
                .build();
    }

    @Override
    public ManufacturerDTO toDTO(Manufacturer BOM) {
        return ManufacturerDTO.builder()
                .id(BOM.getId())
                .name(BOM.getName())
                .country(countryConverter.toDTO(BOM.getCountry()))
                .build();
    }
}
