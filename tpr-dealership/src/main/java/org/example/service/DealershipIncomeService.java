package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.connector.AuctionIncomeConnector;
import org.example.dto.db.DealDTO;
import org.example.dto.web.response.IncomeResponse;
import org.example.repository.DealRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealershipIncomeService {

    private final DealRepository dealRepository;

    private final AuctionIncomeConnector auctionIncomeConnector;

    public IncomeResponse incomeByYear(Integer year) {
        List<DealDTO> dealDTOList = dealRepository.findAllDealsByYear(year);
        Float dealershipIncome = 0F;
        for (DealDTO dealDTO : dealDTOList) {
            dealershipIncome += dealDTO.getPaid();
        }

        Float auctionIncome = auctionIncomeConnector.getByYear(year);

        return IncomeResponse.builder()
                .dealership(dealershipIncome)
                .auction(auctionIncome)
                .total(dealershipIncome + auctionIncome)
                .build();
    }
}
