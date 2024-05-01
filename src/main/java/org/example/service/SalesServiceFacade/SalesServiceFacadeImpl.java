package org.example.service.SalesServiceFacade;

import lombok.RequiredArgsConstructor;
import org.example.bom.Client;
import org.example.bom.Deal;
import org.example.bom.Employee;
import org.example.bom.Vehicle;
import org.example.dto.web.DealRequest;
import org.example.exception.NotFoundException;
import org.example.exception.VehicleOutOfStockException;
import org.example.service.ClientService.ClientService;
import org.example.service.DealService.DealService;
import org.example.service.EmployeeService.EmployeeService;
import org.example.service.VehicleService.VehicleService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class SalesServiceFacadeImpl implements SalesServiceFacade {

    private final VehicleService vehicleService;

    private final ClientService clientService;

    private final EmployeeService employeeService;

    private final DealService dealService;

    @Override
    public Deal createDeal(DealRequest dealRequest) throws NotFoundException, VehicleOutOfStockException {
        Vehicle vehicle = vehicleService.findById(dealRequest.getVehicleId());
        if (!vehicleService.isAvailable(vehicle.getId())) {
            throw new VehicleOutOfStockException(STR."Vehicle with id \{vehicle.getId()} is out of stock");
        }
        Client client = clientService.findById(dealRequest.getClientId());
        Employee employee = employeeService.findById(dealRequest.getEmployeeId());

        vehicle.setAmount(vehicle.getAmount() - 1);
        vehicle = vehicleService.update(vehicle);

        Float totalPrice = vehicle.getPrice() * (1 - ((float) client.getDiscount() / 100));

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Deal deal = new Deal(null, vehicle, client, employee, timestamp, totalPrice);

        return dealService.save(deal);
    }

    @Override
    public Client addClient(Client client) {
        return null;
    }
}
