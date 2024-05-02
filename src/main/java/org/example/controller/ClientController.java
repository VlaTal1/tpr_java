package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.bom.Client;
import org.example.exception.ClientAlreadyExistsException;
import org.example.exception.PhoneAlreadyUsedException;
import org.example.service.SalesServiceFacade.SalesServiceFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client")
public class ClientController {

    private final SalesServiceFacade salesServiceFacade;

    @PostMapping("/")
    public ResponseEntity<Client> add(
            @RequestBody Client client) throws PhoneAlreadyUsedException, ClientAlreadyExistsException {
        Client savedClient = salesServiceFacade.addClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }
}
