package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.bom.Client;
import org.example.converter.Converter;
import org.example.dto.db.ClientDTO;
import org.example.exception.ClientNotFoundException;
import org.example.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    private final Converter<ClientDTO, Client> clientConverter;

    public Client findById(Long id) throws ClientNotFoundException {
        ClientDTO clientDTO = clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(STR."Client with id \{id} bot found"));
        return clientConverter.fromDTO(clientDTO);
    }
}
