package org.example.service.ClientService;

import org.example.bom.Client;
import org.example.exception.NotFoundException;

public interface ClientService {

    Client findById(Long id) throws NotFoundException;

    Client save(Client client);

    boolean isPhoneUsed(String phone);

    boolean isClientExists(Client client);
}
