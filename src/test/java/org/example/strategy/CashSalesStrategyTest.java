package org.example.strategy;

import org.example.bom.*;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class CashSalesStrategyTest {

    private final CashSalesStrategy cashSalesStrategy = new CashSalesStrategy();

    @Test
    void sell() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Model model = new Model();
        Color color = new Color();
        Vehicle vehicle = new PassengerCar(1L, 5000, model, color, 30000, 2022);
        Client client = new Client(1L, "John", "Address", "+380984785740", "UA023948274", 3);
        Employee employee = new Employee(1L, "Alex", "+38097847567", "Sales manager");
        Deal deal = Deal.builder()
                .id(1L)
                .vehicle(vehicle)
                .client(client)
                .employee(employee)
                .date(timestamp)
                .build();

        cashSalesStrategy.sell(deal);

        assertEquals(1L, deal.getId());
        assertEquals(vehicle, deal.getVehicle());
        assertEquals(client, deal.getClient());
        assertEquals(employee, deal.getEmployee());
        assertEquals(timestamp, deal.getDate());
        assertEquals(PaymentType.CASH, deal.getPaymentType());
        assertEquals(0, deal.getMonthlyPayment());
        assertEquals(29100, deal.getTotalPrice());
        assertEquals(29100, deal.getPaid());
    }
}