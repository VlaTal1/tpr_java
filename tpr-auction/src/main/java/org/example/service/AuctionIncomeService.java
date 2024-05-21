package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.db.BidHistoryDTO;
import org.example.repository.BidHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionIncomeService {

    private final BidHistoryRepository bidHistoryRepository;

    public Float incomeByYear(Integer year) {
        List<BidHistoryDTO> bidHistoryDTOS = bidHistoryRepository.findMaxBidAmountForEndedAuctionsByYear(year);
        Float income = 0F;
        for (BidHistoryDTO bidHistoryDTO : bidHistoryDTOS) {
            income += bidHistoryDTO.getBid().getAmount();
        }
        return income;
    }
}
