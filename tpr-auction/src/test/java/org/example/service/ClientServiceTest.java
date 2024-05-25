package org.example.service;

import org.example.bom.Client;
import org.example.converter.Converter;
import org.example.dto.db.ClientDTO;
import org.example.exception.ClientNotFoundException;
import org.example.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private Converter<ClientDTO, Client> clientConverter;

    @InjectMocks
    private ClientService clientService;

    private ClientDTO clientDTO;
    private Client client;

    @BeforeEach
    void setUp() {
        clientDTO = ClientDTO.builder()
                .id(1L)
                .name("John")
                .address("Address")
                .phone("+380984785740")
                .passport("UA023948274")
                .build();

        client = Client.builder()
                .id(1L)
                .name("John")
                .address("Address")
                .phone("+380984785740")
                .passport("UA023948274")
                .build();
    }

    @Test
    void findById_success() throws ClientNotFoundException {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientDTO));
        when(clientConverter.fromDTO(clientDTO)).thenReturn(client);

        Client foundClient = clientService.findById(1L);

        assertEquals(client, foundClient);
    }

    @Test
    void findById_clientNotFound() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.findById(1L));
    }
}
