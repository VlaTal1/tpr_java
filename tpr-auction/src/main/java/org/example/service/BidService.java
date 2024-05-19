package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.bom.Auction;
import org.example.bom.AuctionStatus;
import org.example.bom.Client;
import org.example.converter.Converter;
import org.example.dto.db.BidDTO;
import org.example.dto.db.ClientDTO;
import org.example.dto.web.request.BidRequest;
import org.example.exception.AuctionEndedException;
import org.example.exception.AuctionNotFoundException;
import org.example.exception.ClientNotFoundException;
import org.example.repository.BidRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BidService {

    private final BidRepository bidRepository;

    private final AuctionService auctionService;

    private final ClientService clientService;

    private final Converter<ClientDTO, Client> clientConverter;

    public void placeBid(Long auctionId, BidRequest bidRequest) throws AuctionNotFoundException, AuctionEndedException, ClientNotFoundException {
        Auction auction = auctionService.findById(auctionId);
        if (auction.getAuctionStatus() == AuctionStatus.OPENED) {
            Client client = clientService.findById(bidRequest.getClientId());
            BidDTO bidDTO = BidDTO.builder()
                    .client(clientConverter.toDTO(client))
                    .amount(bidRequest.getAmount())
                    .build();
            bidRepository.save(bidDTO);
            auctionService.resetAuctionTimer(auctionId);
        } else {
            throw new RuntimeException("Auction has ended");
        }
    }
}

