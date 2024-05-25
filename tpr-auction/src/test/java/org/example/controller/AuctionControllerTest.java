package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bom.Auction;
import org.example.bom.AuctionStatus;
import org.example.exception.AuctionNotFoundException;
import org.example.exception.BadRequestException;
import org.example.exception.VehicleNotFoundException;
import org.example.exception.VehicleNotUsedException;
import org.example.service.AuctionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuctionController.class)
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class AuctionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuctionService auctionService;

    @Test
    void startAuction_success() throws Exception {
        Long auctionId = 1L;
        doNothing().when(auctionService).startAuction(anyLong());

        mockMvc.perform(post("/api/auctions/{auctionId}/start", auctionId))
                .andExpect(status().isOk());
    }

    @Test
    void startAuction_notFound() throws Exception {
        Long auctionId = 1L;
        doThrow(new AuctionNotFoundException("Auction with id " + auctionId + " not found")).when(auctionService).startAuction(anyLong());

        mockMvc.perform(post("/api/auctions/{auctionId}/start", auctionId))
                .andExpect(status().isNotFound());
    }

    @Test
    void createAuction_success() throws Exception {
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

        when(auctionService.create(any(Auction.class))).thenReturn(auction);

        String auctionJson = objectMapper.writeValueAsString(auction);

        mockMvc.perform(post("/api/auctions/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(auctionJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(auctionJson));
    }

    @Test
    void createAuction_vehicleNotFound() throws Exception {
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

        when(auctionService.create(any(Auction.class))).thenThrow(new VehicleNotFoundException("Vehicle not found"));

        String auctionJson = objectMapper.writeValueAsString(auction);

        mockMvc.perform(post("/api/auctions/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(auctionJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void createAuction_vehicleNotUsed() throws Exception {
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

        when(auctionService.create(any(Auction.class))).thenThrow(new VehicleNotUsedException("Vehicle not used"));

        String auctionJson = objectMapper.writeValueAsString(auction);

        mockMvc.perform(post("/api/auctions/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(auctionJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void createAuction_badRequest() throws Exception {
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

        when(auctionService.create(any(Auction.class))).thenThrow(new BadRequestException("Bad request"));

        String auctionJson = objectMapper.writeValueAsString(auction);

        mockMvc.perform(post("/api/auctions/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(auctionJson))
                .andExpect(status().isNotFound());
    }
}
