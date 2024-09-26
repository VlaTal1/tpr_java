package org.example.service.SalesServiceFacade;

import lombok.RequiredArgsConstructor;
import org.example.bom.*;
import org.example.dto.web.DealRequest;
import org.example.exception.*;
import org.example.service.ClientService.ClientService;
import org.example.service.DealService.DealService;
import org.example.service.EmployeeService.EmployeeService;
import org.example.service.VehicleService.VehicleService;
import org.example.strategy.CashSalesStrategy;
import org.example.strategy.CreditSalesStrategy;
import org.example.strategy.SalesContext;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SalesServiceFacadeImpl implements SalesServiceFacade {

    private final VehicleService vehicleService;

    private final ClientService clientService;

    private final EmployeeService employeeService;

    private final DealService dealService;

    private SalesContext salesContext;

    @Override
    public Deal createDeal(DealRequest dealRequest) throws NotFoundException, VehicleOutOfStockException, VehicleNotFoundException {
        Vehicle vehicle = vehicleService.findById(dealRequest.getVehicleId());
        if (!vehicleService.isAvailable(vehicle.getId())) {
            throw new VehicleOutOfStockException(STR."Vehicle with id \{vehicle.getId()} is out of stock");
        }
        Client client = clientService.findById(dealRequest.getClientId());
        Employee employee = employeeService.findById(dealRequest.getEmployeeId());

        vehicle.setAmount(vehicle.getAmount() - 1);
        vehicle = vehicleService.update(vehicle.getId(), vehicle);

        Timestamp currentDateTime = Timestamp.valueOf(LocalDateTime.now());

        Deal deal = Deal.builder()
                .vehicle(vehicle)
                .client(client)
                .employee(employee)
                .date(currentDateTime)
                .build();

        if (dealRequest.getPaymentType().equals(PaymentType.CASH)) {
            salesContext = new SalesContext(new CashSalesStrategy());
        } else if (dealRequest.getPaymentType().equals(PaymentType.CREDIT)) {
            salesContext = new SalesContext(new CreditSalesStrategy());
        }

        salesContext.executeSales(deal);

        return dealService.save(deal);
    }

    @Override
    public Client addClient(Client client) throws ClientAlreadyExistsException, PhoneAlreadyUsedException {
        if (clientService.isClientExists(client)) {
            throw new ClientAlreadyExistsException(STR."This client already exists");
        }
        if (clientService.isPhoneUsed(client.getPhone())) {
            throw new PhoneAlreadyUsedException(STR."Phone \{client.getPhone()} already used");
        }
        client.setDiscount(0);
        return clientService.save(client);
    }
}
