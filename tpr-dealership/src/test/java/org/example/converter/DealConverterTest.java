package org.example.converter;

import org.example.bom.*;
import org.example.dto.db.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class DealConverterTest {

    @Mock
    private Converter<VehicleDTO, Vehicle> vehicleConverter;

    @Mock
    private Converter<ClientDTO, Client> clientConverter;

    @Mock
    private Converter<EmployeeDTO, Employee> employeeConverter;

    private Converter<DealDTO, Deal> dealConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dealConverter = new DealConverter(vehicleConverter, clientConverter, employeeConverter);
    }

    @Test
    void fromDTO() {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

        VehicleDTO vehicleDTO = new VehicleDTO();
        ClientDTO clientDTO = new ClientDTO();
        EmployeeDTO employeeDTO = new EmployeeDTO();
        DealDTO dealDTO = new DealDTO(1L, vehicleDTO, clientDTO, employeeDTO, timestamp, 10000F, PaymentType.CASH.toString(), 100F, 0F);

        Vehicle vehicle = new PassengerCar();
        Client client = new Client();
        Employee employee = new Employee();

        when(vehicleConverter.fromDTO(vehicleDTO)).thenReturn(vehicle);
        when(clientConverter.fromDTO(clientDTO)).thenReturn(client);
        when(employeeConverter.fromDTO(employeeDTO)).thenReturn(employee);

        Deal actualDeal = dealConverter.fromDTO(dealDTO);

        assertNotNull(actualDeal);
        assertEquals(1L, actualDeal.getId());
        assertEquals(timestamp, actualDeal.getDate());
        assertEquals(10000F, actualDeal.getTotalPrice());
        assertEquals(PaymentType.CASH, actualDeal.getPaymentType());
        assertEquals(100F, actualDeal.getMonthlyPayment());
        assertEquals(0F, actualDeal.getPaid());
        assertEquals(vehicle, actualDeal.getVehicle());
        assertEquals(client, actualDeal.getClient());
        assertEquals(employee, actualDeal.getEmployee());
    }

    @Test
    void toDTO() {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

        Vehicle vehicle = new PassengerCar();
        Client client = new Client();
        Employee employee = new Employee();
        Deal deal = new Deal(1L, vehicle, client, employee, timestamp, 10000F, PaymentType.CASH, 100F, 0F);

        VehicleDTO vehicleDTO = new VehicleDTO();
        ClientDTO clientDTO = new ClientDTO();
        EmployeeDTO employeeDTO = new EmployeeDTO();

        when(vehicleConverter.toDTO(vehicle)).thenReturn(vehicleDTO);
        when(clientConverter.toDTO(client)).thenReturn(clientDTO);
        when(employeeConverter.toDTO(employee)).thenReturn(employeeDTO);

        DealDTO actualDeal = dealConverter.toDTO(deal);

        assertNotNull(actualDeal);
        assertEquals(1L, actualDeal.getId());
        assertEquals(timestamp, actualDeal.getDate());
        assertEquals(10000F, actualDeal.getTotalPrice());
        assertEquals(PaymentType.CASH.toString(), actualDeal.getPaymentType());
        assertEquals(100F, actualDeal.getMonthlyPayment());
        assertEquals(0F, actualDeal.getPaid());
        assertEquals(vehicleDTO, actualDeal.getVehicle());
        assertEquals(clientDTO, actualDeal.getClient());
        assertEquals(employeeDTO, actualDeal.getEmployee());
    }
}