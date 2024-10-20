package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.bom.Auction;
import org.example.bom.AuctionStatus;
import org.example.bom.Bid;
import org.example.bom.Client;
import org.example.converter.BidConverter;
import org.example.converter.Converter;
import org.example.dto.db.AuctionDTO;
import org.example.dto.db.BidDTO;
import org.example.dto.db.BidHistoryDTO;
import org.example.dto.db.ClientDTO;
import org.example.dto.web.request.BidRequest;
import org.example.exception.*;
import org.example.repository.BidHistoryRepository;
import org.example.repository.BidRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BidService {

    private final BidRepository bidRepository;

    private final BidHistoryRepository bidHistoryRepository;

    private final AuctionService auctionService;

    private final ClientService clientService;

    private final Converter<ClientDTO, Client> clientConverter;

    private final Converter<AuctionDTO, Auction> auctionConverter;

    private final BidConverter bidConverter;

    public void placeBid(Long auctionId, BidRequest bidRequest) throws AuctionNotFoundException, AuctionEndedException, ClientNotFoundException, BadRequestException {
        Auction auction = auctionService.findById(auctionId);
        if (auction.getAuctionStatus() == AuctionStatus.STARTED) {
            Client client = clientService.findById(bidRequest.getClientId());
            validateClient(client);
            AuctionDTO auctionDTO = auctionConverter.toDTO(auction);
            validateBid(auctionDTO, bidRequest.getAmount(), auction.getMinBid());
            BidDTO bidDTO = BidDTO.builder()
                    .client(clientConverter.toDTO(client))
                    .amount(bidRequest.getAmount())
                    .build();
            bidRepository.save(bidDTO);
            BidHistoryDTO bidHistoryDTO = BidHistoryDTO.builder()
                    .bid(bidDTO)
                    .auction(auctionDTO)
                    .time(Timestamp.valueOf(LocalDateTime.now()))
                    .build();
            bidHistoryRepository.save(bidHistoryDTO);
            auctionService.resetAuctionTimer(auctionId);
        } else {
            throw new RuntimeException("Auction has ended");
        }
    }

    public Bid getLastBidByAuctionId(Long auctionId) throws NotFoundException, AuctionNotFoundException {
        Auction auction = auctionService.findById(auctionId);
        AuctionDTO auctionDTO = auctionConverter.toDTO(auction);

        BidHistoryDTO lastBid = bidHistoryRepository.findFirstByAuctionOrderByTimeDesc(auctionDTO);
        if (lastBid == null) {
            Bid bid = new Bid();
            bid.setAmount(auction.getStartPrice());
            return bid;
        }

        return bidConverter.fromDTO(lastBid.getBid());
    }

    private void validateClient(Client client) throws ClientNotFoundException {
        if (client == null)
            throw new ClientNotFoundException("Client not found");
    }

    private void validateBid(AuctionDTO auctionDTO, Float amount, Float minBid) throws BadRequestException {
        BidHistoryDTO lastBid = bidHistoryRepository.findFirstByAuctionOrderByTimeDesc(auctionDTO);
        if (lastBid != null && amount - lastBid.getBid().getAmount() < minBid)
            throw new BadRequestException(STR."Bid amount cannot be less then min bid [\{minBid}]");
        if (lastBid != null && lastBid.getBid().getAmount() >= amount)
            throw new BadRequestException("Bid amount cannot be less then last bid");

    }
}

