package org.example.converter;

import org.example.bom.Auction;
import org.example.bom.AuctionStatus;
import org.example.dto.db.AuctionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuctionConverterTest {

    private Converter<AuctionDTO, Auction> auctionConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        auctionConverter = new AuctionConverter();
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

        Auction actualAuction = auctionConverter.fromDTO(auctionDTO);

        assertEquals(1L, actualAuction.getId());
        assertEquals(100L, actualAuction.getVehicleId());
        assertEquals(time, actualAuction.getStartTime());
        assertEquals(60, actualAuction.getBidTimeoutSec());
        assertEquals(1000.0f, actualAuction.getStartPrice());
        assertEquals(50.0f, actualAuction.getMinBid());
        assertEquals(AuctionStatus.OPENED, actualAuction.getAuctionStatus());
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

        AuctionDTO actualAuctionDTO = auctionConverter.toDTO(auction);

        assertEquals(1L, actualAuctionDTO.getId());
        assertEquals(100L, actualAuctionDTO.getVehicleId());
        assertEquals(time, actualAuctionDTO.getStartTime());
        assertEquals(60, actualAuctionDTO.getBidTimeoutSec());
        assertEquals(1000.0f, actualAuctionDTO.getStartPrice());
        assertEquals(50.0f, actualAuctionDTO.getMinBid());
        assertEquals("OPENED", actualAuctionDTO.getAuctionStatus());
    }
}
