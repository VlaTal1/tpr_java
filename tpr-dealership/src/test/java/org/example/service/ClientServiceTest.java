package org.example.service;

import org.example.bom.Client;
import org.example.converter.Converter;
import org.example.dto.db.ClientDTO;
import org.example.exception.NotFoundException;
import org.example.repository.ClientRepository;
import org.example.service.ClientService.ClientService;
import org.example.service.ClientService.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ClientServiceTest {

    @Mock
    private Converter<ClientDTO, Client> clientConverter;

    @Mock
    private ClientRepository clientRepository;

    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientService = new ClientServiceImpl(clientConverter, clientRepository);
    }

    @Test
    void findById() throws NotFoundException {
        Client client = new Client(1L, "John", "Address", "+380984785740", "UA023948274", 3);
        ClientDTO clientDTO = new ClientDTO(1L, "John", "Address", "+380984785740", "UA023948274", 3);

        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(clientDTO));
        when(clientConverter.fromDTO(clientDTO)).thenReturn(client);

        Client actualClient = clientService.findById(client.getId());

        assertEquals(client, actualClient);
    }

    @Test
    void findById_NotFound() {
        Long id = 1L;

        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> clientService.findById(id));
    }

    @Test
    void save() {
        Client client = new Client(1L, "John", "Address", "+380984785740", "UA023948274", 3);
        ClientDTO clientDTO = new ClientDTO(1L, "John", "Address", "+380984785740", "UA023948274", 3);

        when(clientConverter.toDTO(client)).thenReturn(clientDTO);
        when(clientRepository.save(clientDTO)).thenReturn(clientDTO);
        when(clientConverter.fromDTO(clientDTO)).thenReturn(client);

        Client actualClient = clientService.save(client);

        assertEquals(client, actualClient);
    }

    @Test
    void isPhoneUsed_True() {
        Client client = new Client(1L, "John", "Address", "+380984785740", "UA023948274", 3);
        ClientDTO clientDTO = new ClientDTO(1L, "John", "Address", "+380984785740", "UA023948274", 3);

        when(clientRepository.findByPhone(client.getPhone())).thenReturn(clientDTO);
        when(clientConverter.fromDTO(clientDTO)).thenReturn(client);

        boolean isPhoneUsed = clientService.isPhoneUsed(client.getPhone());

        assertTrue(isPhoneUsed);
    }

    @Test
    void isPhoneUsed_False() {
        Client client = new Client(1L, "John", "Address", "+380984785740", "UA023948274", 3);

        when(clientRepository.findByPhone(client.getPhone())).thenReturn(null);

        boolean isPhoneUsed = clientService.isPhoneUsed(client.getPhone());

        assertFalse(isPhoneUsed);
    }

    @Test
    void isClientExists_True() {
        Client client = new Client(1L, "John", "Address", "+380984785740", "UA023948274", 3);
        ClientDTO clientDTO = new ClientDTO(1L, "John", "Address", "+380984785740", "UA023948274", 3);

        when(clientRepository.findByNameAndAddressAndPhoneAndPassport(
                client.getName(), client.getAddress(), client.getPhone(), client.getPassport()
        )).thenReturn(clientDTO);

        boolean isPhoneUsed = clientService.isClientExists(client);

        assertTrue(isPhoneUsed);
    }

    @Test
    void isClientExists_False() {
        Client client = new Client(1L, "John", "Address", "+380984785740", "UA023948274", 3);

        when(clientRepository.findByNameAndAddressAndPhoneAndPassport(
                client.getName(), client.getAddress(), client.getPhone(), client.getPassport()
        )).thenReturn(null);

        boolean isPhoneUsed = clientService.isClientExists(client);

        assertFalse(isPhoneUsed);
    }
}