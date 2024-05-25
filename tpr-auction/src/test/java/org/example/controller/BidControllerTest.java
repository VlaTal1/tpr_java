package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.web.request.BidRequest;
import org.example.exception.AuctionEndedException;
import org.example.exception.AuctionNotFoundException;
import org.example.exception.BadRequestException;
import org.example.exception.ClientNotFoundException;
import org.example.service.BidService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BidController.class)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
public class BidControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BidService bidService;

    @Test
    void placeBid_success() throws Exception {
        Long auctionId = 1L;
        BidRequest bidRequest = new BidRequest(1L, 200.0f);

        doNothing().when(bidService).placeBid(anyLong(), any(BidRequest.class));

        mockMvc.perform(post("/api/bid/auction/{auctionId}", auctionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bidRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void placeBid_clientNotFound() throws Exception {
        Long auctionId = 1L;
        BidRequest bidRequest = new BidRequest(1L, 200.0f);

        doThrow(new ClientNotFoundException("Client not found")).when(bidService).placeBid(anyLong(), any(BidRequest.class));

        mockMvc.perform(post("/api/bid/auction/{auctionId}", auctionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bidRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void placeBid_auctionNotFound() throws Exception {
        Long auctionId = 1L;
        BidRequest bidRequest = new BidRequest(1L, 200.0f);

        doThrow(new AuctionNotFoundException("Auction not found")).when(bidService).placeBid(anyLong(), any(BidRequest.class));

        mockMvc.perform(post("/api/bid/auction/{auctionId}", auctionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bidRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void placeBid_auctionEnded() throws Exception {
        Long auctionId = 1L;
        BidRequest bidRequest = new BidRequest(1L, 200.0f);

        doThrow(new AuctionEndedException("Auction has ended")).when(bidService).placeBid(anyLong(), any(BidRequest.class));

        mockMvc.perform(post("/api/bid/auction/{auctionId}", auctionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bidRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void placeBid_badRequest() throws Exception {
        Long auctionId = 1L;
        BidRequest bidRequest = new BidRequest(1L, 200.0f);

        doThrow(new BadRequestException("Bad request")).when(bidService).placeBid(anyLong(), any(BidRequest.class));

        mockMvc.perform(post("/api/bid/auction/{auctionId}", auctionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bidRequest)))
                .andExpect(status().isNotFound());
    }
}
