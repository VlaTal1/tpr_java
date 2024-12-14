package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.bom.Auction;
import org.example.bom.AuctionStatus;
import org.example.bom.Vehicle;
import org.example.connector.VehicleConnector;
import org.example.connector.VehicleGrpcConnector;
import org.example.converter.AuctionConverter;
import org.example.dto.db.AuctionDTO;
import org.example.dto.db.BidHistoryDTO;
import org.example.exception.*;
import org.example.repository.AuctionRepository;
import org.example.repository.BidHistoryRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;

    private final BidHistoryRepository bidHistoryRepository;

    private final AuctionConverter auctionConverter;

    private final TimerManager timerManager;

    private final VehicleGrpcConnector vehicleGrpcConnector;

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
        Vehicle vehicle = vehicleGrpcConnector.getVehicleById(auction.getVehicleId());

        validateAuction(auction);
        validateVehicle(vehicle, auction.getVehicleId());

        if (auction.getStartTime() == null) {
            auction.setStartTime(Timestamp.valueOf(LocalDateTime.now().plusHours(1)));
        }

        if (auction.getName() == null) {
            auction.setName(STR."\{vehicle.getModel().getManufacturer().getName()} \{vehicle.getModel().getName()} \{vehicle.getYear()}");
        }

        AuctionDTO auctionDTO = auctionRepository.save(auctionConverter.toDTO(auction));
        return auctionConverter.fromDTO(auctionDTO);
    }

    public List<Auction> getAll() {
        List<AuctionDTO> auctionDTOList = auctionRepository.findAll();
        return auctionDTOList.stream().map(auctionConverter::fromDTO).toList();
    }

    public Auction getById(Long auctionId) throws NotFoundException {
        Optional<AuctionDTO> auctionDTO = auctionRepository.findById(auctionId);
        if (auctionDTO.isEmpty()) {
            throw new NotFoundException(STR."Auction with id \{auctionId} not found");
        }
        return auctionConverter.fromDTO(auctionDTO.get());
    }

    private void validateAuction(Auction auction) throws BadRequestException {
        if (auction.getBidTimeoutSec() < 10)
            throw new BadRequestException("Bid timout cannot not be less than 10 seconds!");
        if (auction.getStartPrice() < 0)
            throw new BadRequestException("Start price cannot be less than 0!");
        if (auction.getMinBid() < 50)
            throw new BadRequestException("Minimum bid cannot be less than 50!");
        if (auction.getAuctionStatus() != AuctionStatus.OPENED)
            throw new BadRequestException("New auction can only have status OPENED!");
    }

    private void validateVehicle(Vehicle vehicle, Long vehicleId) throws VehicleNotFoundException, VehicleNotUsedException {
        if (vehicle == null)
            throw new VehicleNotFoundException(STR."Vehicle with id \{vehicleId} not found");
        if (!vehicle.isUsed())
            throw new VehicleNotUsedException(STR."Vehicle with id \{vehicleId} is not used");
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

    public void endAuction(Long auctionId) throws AuctionNotFoundException {
        AuctionDTO auctionDTO = auctionRepository.findById(auctionId).orElseThrow(() -> new AuctionNotFoundException(STR."Auction with id \{auctionId} not found"));
        List<BidHistoryDTO> bidHistoryDTO = bidHistoryRepository.findAllByAuction(auctionDTO);
        auctionDTO.setAuctionStatus(bidHistoryDTO.isEmpty() ? AuctionStatus.CLOSED.name() : AuctionStatus.ENDED.name());
        vehicleGrpcConnector.markVehicleAsSold(auctionDTO.getVehicleId());
        auctionRepository.save(auctionDTO);
        timerManager.cancelTimer(auctionId);
    }
}
