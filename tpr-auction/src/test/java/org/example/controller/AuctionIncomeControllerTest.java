package org.example.controller;

import org.example.service.AuctionIncomeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuctionIncomeController.class)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
public class AuctionIncomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuctionIncomeService auctionIncomeService;

    @Test
    void getByYear_success() throws Exception {
        Integer year = 2024;
        Float expectedIncome = 1250.0F;

        when(auctionIncomeService.incomeByYear(anyInt())).thenReturn(expectedIncome);

        mockMvc.perform(get("/api/income/year/{year}", year)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedIncome.toString()));
    }
}
