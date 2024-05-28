package org.example.converter;

import org.example.bom.Client;
import org.example.dto.db.ClientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientConverterTest {

    private Converter<ClientDTO, Client> clientConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientConverter = new ClientConverter();
    }

    @Test
    void fromDTO() {
        ClientDTO clientDTO = ClientDTO.builder()
                .id(1L)
                .name("John")
                .address("Address")
                .phone("+380984785740")
                .passport("UA023948274")
                .build();

        Client actualClient = clientConverter.fromDTO(clientDTO);

        assertEquals(1L, actualClient.getId());
        assertEquals("John", actualClient.getName());
        assertEquals("Address", actualClient.getAddress());
        assertEquals("+380984785740", actualClient.getPhone());
        assertEquals("UA023948274", actualClient.getPassport());
    }

    @Test
    void toDTO() {
        Client client = Client.builder()
                .id(1L)
                .name("John")
                .address("Address")
                .phone("+380984785740")
                .passport("UA023948274")
                .build();

        ClientDTO actualClientDTO = clientConverter.toDTO(client);

        assertEquals(1L, actualClientDTO.getId());
        assertEquals("John", actualClientDTO.getName());
        assertEquals("Address", actualClientDTO.getAddress());
        assertEquals("+380984785740", actualClientDTO.getPhone());
        assertEquals("UA023948274", actualClientDTO.getPassport());
    }
}
