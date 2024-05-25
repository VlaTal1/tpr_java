package org.example.service;

import org.example.bom.*;
import org.example.connector.VehicleConnector;
import org.example.converter.AuctionConverter;
import org.example.dto.db.AuctionDTO;
import org.example.dto.db.BidHistoryDTO;
import org.example.exception.*;
import org.example.repository.AuctionRepository;
import org.example.repository.BidHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuctionServiceTest {

    @Mock
    private AuctionRepository auctionRepository;

    @Mock
    private BidHistoryRepository bidHistoryRepository;

    @Mock
    private AuctionConverter auctionConverter;

    @Mock
    private TimerManager timerManager;

    @Mock
    private VehicleConnector vehicleConnector;

    @InjectMocks
    private AuctionService auctionService;

    @Test
    void startAuction() throws AuctionNotFoundException {
        Long auctionId = 1L;
        AuctionDTO auctionDTO = AuctionDTO.builder()
                .id(auctionId)
                .auctionStatus(AuctionStatus.OPENED.name())
                .bidTimeoutSec(10)
                .build();
        Auction auction = Auction.builder()
                .id(auctionId)
                .auctionStatus(AuctionStatus.STARTED)
                .bidTimeoutSec(10)
                .build();

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auctionDTO));
        when(auctionConverter.fromDTO(auctionDTO)).thenReturn(auction);

        auctionService.startAuction(auctionId);

        verify(auctionRepository).save(any(AuctionDTO.class));
        verify(timerManager).scheduleTimer(eq(auctionId), any(Runnable.class), anyLong(), eq(TimeUnit.SECONDS));
    }

    @Test
    void startAuction_AuctionNotFound() {
        Long auctionId = 1L;

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.empty());

        assertThrows(AuctionNotFoundException.class, () -> auctionService.startAuction(auctionId));
    }

    @Test
    void findById() throws AuctionNotFoundException {
        Long auctionId = 1L;
        AuctionDTO auctionDTO = AuctionDTO.builder().id(auctionId).build();
        Auction auction = Auction.builder().id(auctionId).build();

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auctionDTO));
        when(auctionConverter.fromDTO(auctionDTO)).thenReturn(auction);

        Auction result = auctionService.findById(auctionId);

        assertEquals(auction, result);
    }

    @Test
    void findById_AuctionNotFound() {
        Long auctionId = 1L;

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.empty());

        assertThrows(AuctionNotFoundException.class, () -> auctionService.findById(auctionId));
    }

    @Test
    void create() throws VehicleNotFoundException, VehicleNotUsedException, BadRequestException {
        Auction auction = Auction.builder()
                .vehicleId(100L)
                .bidTimeoutSec(60)
                .startPrice(1000.0f)
                .minBid(50.0f)
                .auctionStatus(AuctionStatus.OPENED)
                .build();
        AuctionDTO auctionDTO = AuctionDTO.builder().build();
        Vehicle vehicle = new PassengerCar(1L, 5000, new Model(), new Color(), 6000, 2022, true);

        when(vehicleConnector.get(auction.getVehicleId())).thenReturn(vehicle);
        when(auctionRepository.save(any(AuctionDTO.class))).thenReturn(auctionDTO);
        when(auctionConverter.fromDTO(auctionDTO)).thenReturn(auction);
        when(auctionConverter.toDTO(auction)).thenReturn(auctionDTO);

        Auction result = auctionService.create(auction);

        assertEquals(auction, result);
    }

    @Test
    void create_InvalidAuction() {
        Auction auction = Auction.builder()
                .vehicleId(100L)
                .bidTimeoutSec(5) // Invalid bid timeout
                .startPrice(1000.0f)
                .minBid(50.0f)
                .auctionStatus(AuctionStatus.OPENED)
                .build();
        Vehicle vehicle = new PassengerCar(1L, 5000, new Model(), new Color(), 6000, 2022, true);

        when(vehicleConnector.get(anyLong())).thenReturn(vehicle);

        assertThrows(BadRequestException.class, () -> auctionService.create(auction));
    }

    @Test
    void resetAuctionTimer() throws AuctionNotFoundException, AuctionEndedException {
        Long auctionId = 1L;
        AuctionDTO auctionDTO = AuctionDTO.builder()
                .id(auctionId)
                .auctionStatus(AuctionStatus.STARTED.name())
                .bidTimeoutSec(10)
                .build();
        Auction auction = Auction.builder()
                .id(auctionId)
                .auctionStatus(AuctionStatus.STARTED)
                .bidTimeoutSec(10)
                .build();

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auctionDTO));
        when(auctionConverter.fromDTO(auctionDTO)).thenReturn(auction);

        auctionService.resetAuctionTimer(auctionId);

        verify(timerManager).cancelTimer(auctionId);
        verify(timerManager).scheduleTimer(eq(auctionId), any(Runnable.class), anyLong(), eq(TimeUnit.SECONDS));
    }

    @Test
    void resetAuctionTimer_AuctionEnded() {
        Long auctionId = 1L;
        AuctionDTO auctionDTO = AuctionDTO.builder()
                .id(auctionId)
                .auctionStatus(AuctionStatus.ENDED.name())
                .build();

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auctionDTO));

        assertThrows(AuctionEndedException.class, () -> auctionService.resetAuctionTimer(auctionId));
    }

    @Test
    void endAuction() throws AuctionNotFoundException {
        Long auctionId = 1L;
        AuctionDTO auctionDTO = AuctionDTO.builder().id(auctionId).build();
        List<BidHistoryDTO> bidHistoryDTOs = List.of();

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auctionDTO));
        when(bidHistoryRepository.findAllByAuction(auctionDTO)).thenReturn(bidHistoryDTOs);

        auctionService.endAuction(auctionId);

        verify(auctionRepository).save(any(AuctionDTO.class));
        verify(timerManager).cancelTimer(auctionId);
    }

    @Test
    void endAuction_AuctionNotFound() {
        Long auctionId = 1L;

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.empty());

        assertThrows(AuctionNotFoundException.class, () -> auctionService.endAuction(auctionId));
    }
}
