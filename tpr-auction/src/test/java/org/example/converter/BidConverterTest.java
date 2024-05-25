package org.example.converter;

import org.example.bom.Bid;
import org.example.bom.Client;
import org.example.dto.db.BidDTO;
import org.example.dto.db.ClientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BidConverterTest {

    @Mock
    private ClientConverter clientConverter;

    @InjectMocks
    private BidConverter bidConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void fromDTO() {
        ClientDTO clientDTO = new ClientDTO(1L, "John", "Address", "+380984785740", "UA023948274");
        Client client = new Client(1L, "John", "Address", "+380984785740", "UA023948274", 3);

        BidDTO bidDTO = BidDTO.builder()
                .id(1L)
                .client(clientDTO)
                .amount(500.0f)
                .build();

        when(clientConverter.fromDTO(clientDTO)).thenReturn(client);

        Bid actualBid = bidConverter.fromDTO(bidDTO);

        assertEquals(1L, actualBid.getId());
        assertEquals(client, actualBid.getClient());
        assertEquals(500.0f, actualBid.getAmount());
    }

    @Test
    void toDTO() {
        ClientDTO clientDTO = new ClientDTO(1L, "John", "Address", "+380984785740", "UA023948274");
        Client client = new Client(1L, "John", "Address", "+380984785740", "UA023948274", 3);

        Bid bid = Bid.builder()
                .id(1L)
                .client(client)
                .amount(500.0f)
                .build();

        when(clientConverter.toDTO(client)).thenReturn(clientDTO);

        BidDTO actualBidDTO = bidConverter.toDTO(bid);

        assertEquals(1L, actualBidDTO.getId());
        assertEquals(clientDTO, actualBidDTO.getClient());
        assertEquals(500.0f, actualBidDTO.getAmount());
    }
}
