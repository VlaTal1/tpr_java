package org.example.bom;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Deal {

    private Long id;

    private Vehicle vehicle;

    private Client client;

    private Employee employee;

    private Timestamp date;

    private Float totalPrice;
}
