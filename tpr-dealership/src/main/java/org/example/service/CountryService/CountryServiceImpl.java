package org.example.service.CountryService;

import lombok.RequiredArgsConstructor;
import org.example.bom.Country;
import org.example.converter.Converter;
import org.example.dto.db.CountryDTO;
import org.example.repository.CountryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final Converter<CountryDTO, Country> countryConverter;

    private final CountryRepository countryRepository;

    @Override
    public Country findById(Long id) {
        CountryDTO countryDTO = countryRepository.findById(id).orElse(null);
        return countryConverter.fromDTO(countryDTO);
    }
}
