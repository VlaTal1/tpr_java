package org.example.service.DealService;

import lombok.RequiredArgsConstructor;
import org.example.bom.Deal;
import org.example.converter.Converter;
import org.example.dto.db.DealDTO;
import org.example.repository.DealRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final Converter<DealDTO, Deal> dealConverter;

    private final DealRepository dealRepository;

    @Override
    public Deal save(Deal deal) {
        DealDTO dealDTO = dealConverter.toDTO(deal);
        DealDTO savedDealDTO = dealRepository.save(dealDTO);
        return dealConverter.fromDTO(savedDealDTO);
    }
}
