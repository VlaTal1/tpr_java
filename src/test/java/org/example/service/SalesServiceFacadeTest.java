package org.example.service;

import org.example.bom.*;
import org.example.dto.web.DealRequest;
import org.example.exception.ClientAlreadyExistsException;
import org.example.exception.NotFoundException;
import org.example.exception.PhoneAlreadyUsedException;
import org.example.exception.VehicleOutOfStockException;
import org.example.service.ClientService.ClientService;
import org.example.service.DealService.DealService;
import org.example.service.EmployeeService.EmployeeService;
import org.example.service.SalesServiceFacade.SalesServiceFacade;
import org.example.service.SalesServiceFacade.SalesServiceFacadeImpl;
import org.example.service.VehicleService.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class SalesServiceFacadeTest {

    @Mock
    private VehicleService vehicleService;

    @Mock
    private ClientService clientService;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private DealService dealService;

    private SalesServiceFacade salesServiceFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        salesServiceFacade = new SalesServiceFacadeImpl(vehicleService, clientService, employeeService, dealService);
    }

    @Test
    void createDeal() throws NotFoundException, VehicleOutOfStockException {
        DealRequest dealRequest = new DealRequest(1L, 1L, 1L);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Model model = new Model();
        Color color = new Color();
        Vehicle vehicle = new PassengerCar(1L, 5000, model, color, 30000, 2022);
        Client client = new Client(1L, "John", "Address", "+380984785740", "UA023948274", 3);
        Employee employee = new Employee(1L, "Alex", "+38097847567", "Sales manager");
        Deal deal = new Deal(1L, vehicle, client, employee, timestamp, 29100F);

        when(vehicleService.findById(vehicle.getId())).thenReturn(vehicle);
        when(vehicleService.isAvailable(vehicle.getId())).thenReturn(true);
        when(clientService.findById(client.getId())).thenReturn(client);
        when(employeeService.findById(employee.getId())).thenReturn(employee);
        when(vehicleService.update(vehicle)).thenReturn(vehicle);
        when(dealService.save(any(Deal.class))).thenReturn(deal);

        Deal actualDeal = salesServiceFacade.createDeal(dealRequest);

        assertEquals(1L, actualDeal.getId());
        assertEquals(4999, vehicle.getAmount());
        assertEquals(29100, actualDeal.getTotalPrice());
        assertEquals(timestamp, actualDeal.getDate());
        assertEquals(vehicle, actualDeal.getVehicle());
        assertEquals(client, actualDeal.getClient());
        assertEquals(employee, actualDeal.getEmployee());
        verify(vehicleService).findById(anyLong());
        verify(vehicleService).isAvailable(anyLong());
        verify(clientService).findById(anyLong());
        verify(employeeService).findById(anyLong());
        verify(vehicleService).update(vehicle);
        verify(dealService).save(any(Deal.class));
    }

    @Test
    void addClient() throws ClientAlreadyExistsException, PhoneAlreadyUsedException {
        Client client = new Client(1L, "John", "Address", "+380984785740", "UA023948274", 0);

        when(clientService.isClientExists(client)).thenReturn(false);
        when(clientService.isPhoneUsed(client.getPhone())).thenReturn(false);
        when(clientService.save(client)).thenReturn(client);

        Client addedClient = salesServiceFacade.addClient(client);

        assertEquals(client, addedClient);
        assertEquals(0, addedClient.getDiscount());

        verify(clientService).isClientExists(client);
        verify(clientService).isPhoneUsed(client.getPhone());
        verify(clientService).save(client);
    }

    @Test
    void addClient_ClientAlreadyExists() {
        Client client = new Client(1L, "John", "Address", "+380984785740", "UA023948274", 0);

        when(clientService.isClientExists(client)).thenReturn(true);

        assertThrows(ClientAlreadyExistsException.class, () -> salesServiceFacade.addClient(client));

        verify(clientService).isClientExists(client);
        verify(clientService, never()).isPhoneUsed(any());
        verify(clientService, never()).save(any());
    }

    @Test
    void addClient_PhoneAlreadyUsed() {
        Client client = new Client(1L, "John", "Address", "+380984785740", "UA023948274", 0);

        when(clientService.isClientExists(client)).thenReturn(false);
        when(clientService.isPhoneUsed(client.getPhone())).thenReturn(true);

        PhoneAlreadyUsedException exception = assertThrows(PhoneAlreadyUsedException.class, () -> salesServiceFacade.addClient(client));
        assertEquals("Phone +380984785740 already used", exception.getMessage());

        verify(clientService).isClientExists(client);
        verify(clientService).isPhoneUsed(client.getPhone());
        verify(clientService, never()).save(any());
    }
}