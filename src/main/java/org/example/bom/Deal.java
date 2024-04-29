package org.example.bom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class Deal {

    private Long id;

    private Vehicle vehicle;

    private Client client;

    private Employee employee;

    private Timestamp date;

    private double totalPrice;
}
