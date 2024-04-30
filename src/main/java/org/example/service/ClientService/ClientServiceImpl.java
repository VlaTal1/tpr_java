package org.example.service.ClientService;

import lombok.RequiredArgsConstructor;
import org.example.exception.NotFoundException;
import org.example.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public void checkById(Long id) throws NotFoundException {
        clientRepository.findById(id).orElseThrow(() -> new NotFoundException(STR."Client with id \{id} not found"));
    }
}
