package org.example.service;

import org.example.dto.db.BidDTO;
import org.example.dto.db.BidHistoryDTO;
import org.example.dto.db.ClientDTO;
import org.example.repository.BidHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class AuctionIncomeServiceTest {

    @Mock
    private BidHistoryRepository bidHistoryRepository;

    @InjectMocks
    private AuctionIncomeService auctionIncomeService;

    @Test
    void incomeByYear() {
        int year = 2024;

        ClientDTO clientDTO = new ClientDTO(1L, "John", "Address", "+380984785740", "UA023948274");
        BidDTO bidDTO1 = BidDTO.builder().id(1L).client(clientDTO).amount(500.0f).build();
        BidDTO bidDTO2 = BidDTO.builder().id(2L).client(clientDTO).amount(750.0f).build();

        BidHistoryDTO bidHistoryDTO1 = BidHistoryDTO.builder().id(1L).bid(bidDTO1).build();
        BidHistoryDTO bidHistoryDTO2 = BidHistoryDTO.builder().id(2L).bid(bidDTO2).build();

        List<BidHistoryDTO> bidHistoryDTOS = Arrays.asList(bidHistoryDTO1, bidHistoryDTO2);

        when(bidHistoryRepository.findMaxBidAmountForEndedAuctionsByYear(year)).thenReturn(bidHistoryDTOS);

        Float income = auctionIncomeService.incomeByYear(year);

        assertEquals(1250.0F, income);
    }
}
