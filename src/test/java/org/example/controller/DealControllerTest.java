package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bom.Deal;
import org.example.bom.PaymentType;
import org.example.dto.web.DealRequest;
import org.example.exception.VehicleOutOfStockException;
import org.example.service.SalesServiceFacade.SalesServiceFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DealController.class)
class DealControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SalesServiceFacade salesServiceFacade;

    @Test
    void add() throws Exception {
        DealRequest dealRequest = new DealRequest(1L, 1L, 1L, PaymentType.CASH);
        Deal deal = new Deal();

        when(salesServiceFacade.createDeal(any(DealRequest.class))).thenReturn(deal);

        String dealRequestJson = objectMapper.writeValueAsString(dealRequest);
        String dealJson = objectMapper.writeValueAsString(deal);

        mockMvc.perform(post("/api/deal/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dealRequestJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(dealJson));
    }

    @Test
    void add_VehicleOutOfStockException() throws Exception {
        DealRequest dealRequest = new DealRequest(1L, 1L, 1L, PaymentType.CASH);

        when(salesServiceFacade.createDeal(any(DealRequest.class)))
                .thenThrow(new VehicleOutOfStockException("Vehicle is out of stock"));

        String dealRequestJson = objectMapper.writeValueAsString(dealRequest);

        mockMvc.perform(post("/api/deal/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dealRequestJson))
                .andExpect(status().isBadRequest());
    }
}