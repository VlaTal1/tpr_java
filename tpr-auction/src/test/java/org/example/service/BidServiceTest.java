package org.example.service;

import org.example.bom.Auction;
import org.example.bom.AuctionStatus;
import org.example.bom.Client;
import org.example.converter.Converter;
import org.example.dto.db.AuctionDTO;
import org.example.dto.db.BidDTO;
import org.example.dto.db.BidHistoryDTO;
import org.example.dto.db.ClientDTO;
import org.example.dto.web.request.BidRequest;
import org.example.exception.AuctionEndedException;
import org.example.exception.AuctionNotFoundException;
import org.example.exception.BadRequestException;
import org.example.exception.ClientNotFoundException;
import org.example.repository.BidHistoryRepository;
import org.example.repository.BidRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BidServiceTest {

    @Mock
    private BidRepository bidRepository;

    @Mock
    private BidHistoryRepository bidHistoryRepository;

    @Mock
    private AuctionService auctionService;

    @Mock
    private ClientService clientService;

    @Mock
    private Converter<ClientDTO, Client> clientConverter;

    @Mock
    private Converter<AuctionDTO, Auction> auctionConverter;

    @InjectMocks
    private BidService bidService;

    private Auction auction;

    private Client client;

    private AuctionDTO auctionDTO;

    private ClientDTO clientDTO;

    @BeforeEach
    void setUp() {
        auction = Auction.builder()
                .id(1L)
                .auctionStatus(AuctionStatus.STARTED)
                .minBid(50.0f)
                .build();

        client = Client.builder()
                .id(1L)
                .name("John")
                .address("Address")
                .phone("+380984785740")
                .passport("UA023948274")
                .build();

        auctionDTO = AuctionDTO.builder()
                .id(1L)
                .auctionStatus(AuctionStatus.STARTED.name())
                .minBid(50.0f)
                .build();

        clientDTO = ClientDTO.builder()
                .id(1L)
                .name("John")
                .address("Address")
                .phone("+380984785740")
                .passport("UA023948274")
                .build();
    }

    @Test
    void placeBid_success() throws AuctionNotFoundException, AuctionEndedException, ClientNotFoundException, BadRequestException {
        Timestamp time = Timestamp.valueOf(LocalDateTime.now());
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
        BidRequest bidRequest = new BidRequest(1L, 23400.0f);

        when(auctionService.findById(auction.getId())).thenReturn(auction);
        when(clientService.findById(bidRequest.getClientId())).thenReturn(client);
        when(bidHistoryRepository.findFirstByAuctionOrderByTimeDesc(any())).thenReturn(bidHistoryDTO);

        bidService.placeBid(auction.getId(), bidRequest);

        verify(bidRepository).save(any(BidDTO.class));
        verify(bidHistoryRepository).save(any(BidHistoryDTO.class));
        verify(auctionService).resetAuctionTimer(auction.getId());
    }

    @Test
    void placeBid_auctionNotFound() throws AuctionNotFoundException {
        BidRequest bidRequest = new BidRequest(1L, 200.0f);

        when(auctionService.findById(auction.getId())).thenThrow(new AuctionNotFoundException("Auction not found"));

        assertThrows(AuctionNotFoundException.class, () -> bidService.placeBid(auction.getId(), bidRequest));
    }

    @Test
    void placeBid_clientNotFound() throws AuctionNotFoundException, ClientNotFoundException {
        BidRequest bidRequest = new BidRequest(1L, 200.0f);

        when(auctionService.findById(auction.getId())).thenReturn(auction);
        when(clientService.findById(bidRequest.getClientId())).thenThrow(new ClientNotFoundException("Client not found"));

        assertThrows(ClientNotFoundException.class, () -> bidService.placeBid(auction.getId(), bidRequest));
    }

    @Test
    void placeBid_bidLessThanMinBid() throws AuctionNotFoundException, ClientNotFoundException {
        BidRequest bidRequest = new BidRequest(1L, 70.0f);
        BidDTO lastBidDTO = BidDTO.builder().amount(150.0f).build();
        BidHistoryDTO lastBid = BidHistoryDTO.builder().bid(lastBidDTO).build();

        when(auctionService.findById(auction.getId())).thenReturn(auction);
        when(clientService.findById(bidRequest.getClientId())).thenReturn(client);
        when(bidHistoryRepository.findFirstByAuctionOrderByTimeDesc(any())).thenReturn(lastBid);

        assertThrows(BadRequestException.class, () -> bidService.placeBid(auction.getId(), bidRequest));
    }

    @Test
    void placeBid_bidLessThanLastBid() throws AuctionNotFoundException, ClientNotFoundException {
        BidRequest bidRequest = new BidRequest(1L, 100.0f);
        BidDTO lastBidDTO = BidDTO.builder().amount(150.0f).build();
        BidHistoryDTO lastBid = BidHistoryDTO.builder().bid(lastBidDTO).build();

        when(auctionService.findById(auction.getId())).thenReturn(auction);
        when(clientService.findById(bidRequest.getClientId())).thenReturn(client);
        when(bidHistoryRepository.findFirstByAuctionOrderByTimeDesc(any())).thenReturn(lastBid);

        assertThrows(BadRequestException.class, () -> bidService.placeBid(auction.getId(), bidRequest));
    }

    @Test
    void placeBid_auctionEnded() throws AuctionNotFoundException {
        auction.setAuctionStatus(AuctionStatus.ENDED);
        BidRequest bidRequest = new BidRequest(1L, 200.0f);

        when(auctionService.findById(auction.getId())).thenReturn(auction);

        assertThrows(RuntimeException.class, () -> bidService.placeBid(auction.getId(), bidRequest));
    }
}
