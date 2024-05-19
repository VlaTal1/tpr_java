package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.bom.Auction;
import org.example.bom.AuctionStatus;
import org.example.converter.AuctionConverter;
import org.example.dto.db.AuctionDTO;
import org.example.exception.AuctionEndedException;
import org.example.exception.AuctionNotFoundException;
import org.example.repository.AuctionRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;

    private final AuctionConverter auctionConverter;

    private final TimerManager timerManager;

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

    private void endAuction(Long auctionId) throws AuctionNotFoundException {
        AuctionDTO auctionDTO = auctionRepository.findById(auctionId).orElseThrow(() -> new AuctionNotFoundException(STR."Auction with id \{auctionId} not found"));
        auctionDTO.setAuctionStatus(AuctionStatus.ENDED.name());
        auctionRepository.save(auctionDTO);
        timerManager.cancelTimer(auctionId);
    }
}
