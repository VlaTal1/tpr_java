package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bom.Client;
import org.example.exception.ClientAlreadyExistsException;
import org.example.exception.PhoneAlreadyUsedException;
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

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SalesServiceFacade salesServiceFacade;

    @Test
    void addTest() throws Exception {
        Client client = new Client(1L, "John", "Address", "+380984785740", "UA023948274", 3);
        when(salesServiceFacade.addClient(any(Client.class))).thenReturn(client);

        String clientJson = objectMapper.writeValueAsString(client);

        mockMvc.perform(post("/api/client/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(clientJson));
    }

    @Test
    void addTest_PhoneAlreadyUsedException() throws Exception {
        Client client = new Client(1L, "John", "Address", "+380984785740", "UA023948274", 3);
        when(salesServiceFacade.addClient(any(Client.class))).thenThrow(new PhoneAlreadyUsedException("Phone already used"));

        String clientJson = objectMapper.writeValueAsString(client);

        mockMvc.perform(post("/api/client/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson))
                .andExpect(status().isConflict());
    }

    @Test
    void addTest_ClientAlreadyExistsException() throws Exception {
        Client client = new Client(1L, "John", "Address", "+380984785740", "UA023948274", 3);
        when(salesServiceFacade.addClient(any(Client.class))).thenThrow(new ClientAlreadyExistsException("Client already exists"));

        String clientJson = objectMapper.writeValueAsString(client);

        mockMvc.perform(post("/api/client/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson))
                .andExpect(status().isConflict());
    }
}