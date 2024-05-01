package org.example.converter;

import org.example.bom.Client;
import org.example.dto.db.ClientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientConverterTest {

    private Converter<ClientDTO, Client> clientConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientConverter = new ClientConverter();
    }

    @Test
    void fromDTO() {
        ClientDTO clientDTO = new ClientDTO(1L, "John", "Address", "+380984785740", "UA023948274", 3);

        Client actualClient = clientConverter.fromDTO(clientDTO);

        assertEquals(1L, actualClient.getId());
        assertEquals("John", actualClient.getName());
        assertEquals("Address", actualClient.getAddress());
        assertEquals("+380984785740", actualClient.getPhone());
        assertEquals("UA023948274", actualClient.getPassport());
        assertEquals(3, actualClient.getDiscount());
    }

    @Test
    void toDTO() {
        Client client = new Client(1L, "John", "Address", "+380984785740", "UA023948274", 3);

        ClientDTO actualClient = clientConverter.toDTO(client);

        assertEquals(1L, actualClient.getId());
        assertEquals("John", actualClient.getName());
        assertEquals("Address", actualClient.getAddress());
        assertEquals("+380984785740", actualClient.getPhone());
        assertEquals("UA023948274", actualClient.getPassport());
        assertEquals(3, actualClient.getDiscount());
    }
}