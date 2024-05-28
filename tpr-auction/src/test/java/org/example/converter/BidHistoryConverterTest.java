package org.example.converter;

import org.example.bom.*;
import org.example.dto.db.AuctionDTO;
import org.example.dto.db.BidDTO;
import org.example.dto.db.BidHistoryDTO;
import org.example.dto.db.ClientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BidHistoryConverterTest {

    @Mock
    private AuctionConverter auctionConverter;

    @Mock
    private BidConverter bidConverter;

    @InjectMocks
    private BidHistoryConverter bidHistoryConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void fromDTO() {
        Timestamp time = Timestamp.valueOf(LocalDateTime.now());
        AuctionDTO auctionDTO = AuctionDTO.builder()
                .id(1L)
                .vehicleId(100L)
                .startTime(time)
                .bidTimeoutSec(60)
                .startPrice(1000.0f)
                .minBid(50.0f)
                .auctionStatus("OPENED")
                .build();

        BidDTO bidDTO = BidDTO.builder()
                .id(1L)
                .client(new ClientDTO(1L, "John", "Address", "+380984785740", "UA023948274"))
                .amount(500.0f)
                .build();

        BidHistoryDTO bidHistoryDTO = BidHistoryDTO.builder()
                .id(1L)
                .auction(auctionDTO)
                .bid(bidDTO)
                .time(time)
                .build();

        Auction auction = Auction.builder()
                .id(1L)
                .vehicleId(100L)
                .startTime(time)
                .bidTimeoutSec(60)
                .startPrice(1000.0f)
                .minBid(50.0f)
                .auctionStatus(AuctionStatus.OPENED)
                .build();

        Bid bid = Bid.builder()
                .id(1L)
                .client(new Client(1L, "John", "Address", "+380984785740", "UA023948274", 3))
                .amount(500.0f)
                .build();

        when(auctionConverter.fromDTO(auctionDTO)).thenReturn(auction);
        when(bidConverter.fromDTO(bidDTO)).thenReturn(bid);

        BidHistory actualBidHistory = bidHistoryConverter.fromDTO(bidHistoryDTO);

        assertEquals(1L, actualBidHistory.getId());
        assertEquals(auction, actualBidHistory.getAuction());
        assertEquals(bid, actualBidHistory.getBid());
        assertEquals(time, actualBidHistory.getTime());
    }

    @Test
    void toDTO() {
        Timestamp time = Timestamp.valueOf(LocalDateTime.now());
        Auction auction = Auction.builder()
                .id(1L)
                .vehicleId(100L)
                .startTime(time)
                .bidTimeoutSec(60)
                .startPrice(1000.0f)
                .minBid(50.0f)
                .auctionStatus(AuctionStatus.OPENED)
                .build();

        Bid bid = Bid.builder()
                .id(1L)
                .client(new Client(1L, "John", "Address", "+380984785740", "UA023948274", 3))
                .amount(500.0f)
                .build();

        BidHistory bidHistory = BidHistory.builder()
                .id(1L)
                .auction(auction)
                .bid(bid)
                .time(time)
                .build();

        AuctionDTO auctionDTO = AuctionDTO.builder()
                .id(1L)
                .vehicleId(100L)
                .startTime(time)
                .bidTimeoutSec(60)
                .startPrice(1000.0f)
                .minBid(50.0f)
                .auctionStatus("OPENED")
                .build();

        BidDTO bidDTO = BidDTO.builder()
                .id(1L)
                .client(new ClientDTO(1L, "John", "Address", "+380984785740", "UA023948274"))
                .amount(500.0f)
                .build();

        when(auctionConverter.toDTO(auction)).thenReturn(auctionDTO);
        when(bidConverter.toDTO(bid)).thenReturn(bidDTO);

        BidHistoryDTO actualBidHistoryDTO = bidHistoryConverter.toDTO(bidHistory);

        assertEquals(1L, actualBidHistoryDTO.getId());
        assertEquals(auctionDTO, actualBidHistoryDTO.getAuction());
        assertEquals(bidDTO, actualBidHistoryDTO.getBid());
        assertEquals(time, actualBidHistoryDTO.getTime());
    }
}
