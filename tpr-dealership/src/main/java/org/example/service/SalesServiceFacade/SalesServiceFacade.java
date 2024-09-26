package org.example.service.SalesServiceFacade;

import org.example.bom.Client;
import org.example.bom.Deal;
import org.example.dto.web.DealRequest;
import org.example.exception.*;

public interface SalesServiceFacade {

    Deal createDeal(DealRequest dealRequest) throws NotFoundException, VehicleOutOfStockException, VehicleNotFoundException;

    Client addClient(Client client) throws ClientAlreadyExistsException, PhoneAlreadyUsedException;
}
