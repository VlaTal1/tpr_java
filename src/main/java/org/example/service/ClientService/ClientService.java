package org.example.service.ClientService;

import org.example.exception.NotFoundException;

public interface ClientService {

    void checkById(Long id) throws NotFoundException;
}
