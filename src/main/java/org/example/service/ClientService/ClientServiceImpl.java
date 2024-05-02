package org.example.service.ClientService;

import lombok.RequiredArgsConstructor;
import org.example.bom.Client;
import org.example.converter.Converter;
import org.example.dto.db.ClientDTO;
import org.example.exception.NotFoundException;
import org.example.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final Converter<ClientDTO, Client> clientConverter;

    private final ClientRepository clientRepository;

    @Override
    public Client findById(Long id) throws NotFoundException {
        ClientDTO clientDTO = clientRepository.findById(id).orElseThrow(() -> new NotFoundException(STR."Client with id \{id} not found"));
        return clientConverter.fromDTO(clientDTO);
    }

    @Override
    public Client save(Client client) {
        ClientDTO clientDTO = clientConverter.toDTO(client);
        ClientDTO savedClientDTO = clientRepository.save(clientDTO);
        return clientConverter.fromDTO(savedClientDTO);
    }

    @Override
    public boolean isPhoneUsed(String phone) {
        ClientDTO clientDTO = clientRepository.findByPhone(phone);
        return clientDTO != null;
    }

    @Override
    public boolean isClientExists(Client client) {
        ClientDTO clientDTO = clientRepository.findByNameAndAddressAndPhoneAndPassport(
                client.getName(), client.getAddress(), client.getPhone(), client.getPassport());
        return clientDTO != null;
    }
}
