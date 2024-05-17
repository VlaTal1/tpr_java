package org.example.service.SalesServiceFacade;

import org.example.bom.Client;
import org.example.bom.Deal;
import org.example.dto.web.DealRequest;
import org.example.exception.ClientAlreadyExistsException;
import org.example.exception.NotFoundException;
import org.example.exception.PhoneAlreadyUsedException;
import org.example.exception.VehicleOutOfStockException;

public interface SalesServiceFacade {

    Deal createDeal(DealRequest dealRequest) throws NotFoundException, VehicleOutOfStockException;

    Client addClient(Client client) throws ClientAlreadyExistsException, PhoneAlreadyUsedException;
}
