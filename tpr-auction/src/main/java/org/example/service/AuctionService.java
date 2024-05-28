package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.bom.Auction;
import org.example.bom.AuctionStatus;
import org.example.bom.Vehicle;
import org.example.connector.VehicleConnector;
import org.example.converter.AuctionConverter;
import org.example.dto.db.AuctionDTO;
import org.example.dto.db.BidHistoryDTO;
import org.example.exception.*;
import org.example.repository.AuctionRepository;
import org.example.repository.BidHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;

    private final BidHistoryRepository bidHistoryRepository;

    private final AuctionConverter auctionConverter;

    private final TimerManager timerManager;

    private final VehicleConnector vehicleConnector;

    public void startAuction(Long auctionId) throws AuctionNotFoundException {
        AuctionDTO auctionDTO = auctionRepository.findById(auctionId).orElseThrow(() -> new AuctionNotFoundException(STR."Auction with id \{auctionId} not found"));
        auctionDTO.setAuctionStatus(AuctionStatus.STARTED.name());
        auctionRepository.save(auctionDTO);
        startAuctionTimer(auctionConverter.fromDTO(auctionDTO));
    }

    public Auction findById(Long auctionId) throws AuctionNotFoundException {
        AuctionDTO auctionDTO = auctionRepository.findById(auctionId).orElseThrow(() -> new AuctionNotFoundException(STR."Auction with id \{auctionId} not found"));
        return auctionConverter.fromDTO(auctionDTO);
    }

    public Auction create(Auction auction) throws VehicleNotFoundException, VehicleNotUsedException, BadRequestException {
        validateAuction(auction);
        AuctionDTO auctionDTO = auctionRepository.save(auctionConverter.toDTO(auction));
        return auctionConverter.fromDTO(auctionDTO);
    }

    private void validateAuction(Auction auction) throws VehicleNotFoundException, VehicleNotUsedException, BadRequestException {
        Vehicle vehicle = vehicleConnector.get(auction.getVehicleId());
        if (vehicle == null)
            throw new VehicleNotFoundException(STR."Vehicle with id \{auction.getVehicleId()} not found");
        if (!vehicle.isUsed())
            throw new VehicleNotUsedException(STR."Vehicle with id \{auction.getVehicleId()} is not used");
        if (auction.getBidTimeoutSec() < 10)
            throw new BadRequestException("Bid timout cannot not be less than 10 seconds!");
        if (auction.getStartPrice() < 0)
            throw new BadRequestException("Start price cannot be less than 0!");
        if (auction.getMinBid() < 50)
            throw new BadRequestException("Minimum bid cannot be less than 50!");
        if (auction.getAuctionStatus() != AuctionStatus.OPENED)
            throw new BadRequestException("New auction can only have status OPENED!");
    }

    private void startAuctionTimer(Auction auction) {
        long timeout = auction.getBidTimeoutSec();
        timerManager.scheduleTimer(auction.getId(), () -> {
            try {
                endAuction(auction.getId());
            } catch (AuctionNotFoundException e) {
                throw new RuntimeException(e);
            }
        }, timeout, TimeUnit.SECONDS);
    }

    public void resetAuctionTimer(Long auctionId) throws AuctionNotFoundException, AuctionEndedException {
        AuctionDTO auctionDTO = auctionRepository.findById(auctionId).orElseThrow(() -> new AuctionNotFoundException(STR."Auction with id \{auctionId} not found"));
        if (Objects.equals(auctionDTO.getAuctionStatus(), AuctionStatus.STARTED.name())) {
            timerManager.cancelTimer(auctionId);
            startAuctionTimer(auctionConverter.fromDTO(auctionDTO));
        } else {
            throw new AuctionEndedException(STR."Auction with id \{auctionId} ended");
        }
    }

    protected void endAuction(Long auctionId) throws AuctionNotFoundException {
        AuctionDTO auctionDTO = auctionRepository.findById(auctionId).orElseThrow(() -> new AuctionNotFoundException(STR."Auction with id \{auctionId} not found"));
        List<BidHistoryDTO> bidHistoryDTO = bidHistoryRepository.findAllByAuction(auctionDTO);
        auctionDTO.setAuctionStatus(bidHistoryDTO.isEmpty() ? AuctionStatus.CLOSED.name() : AuctionStatus.ENDED.name());
        auctionRepository.save(auctionDTO);
        timerManager.cancelTimer(auctionId);
    }
}
