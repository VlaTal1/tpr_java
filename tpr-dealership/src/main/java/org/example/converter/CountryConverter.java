package org.example.converter;

import org.example.bom.Country;
import org.example.dto.db.CountryDTO;
import org.springframework.stereotype.Component;

@Component
public class CountryConverter implements Converter<CountryDTO, Country> {

    @Override
    public Country fromDTO(CountryDTO DTO) {
        if (DTO == null) return null;
        return Country.builder()
                .id(DTO.getId())
                .name(DTO.getName())
                .build();
    }

    @Override
    public CountryDTO toDTO(Country BOM) {
        return CountryDTO.builder()
                .id(BOM.getId())
                .name(BOM.getName())
                .build();
    }
}
