package org.example.bom;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class Deal {

    @JsonProperty
    private Long id;

    private Vehicle vehicle;

    private Client client;

    private Employee employee;

    @JsonProperty
    private Timestamp date;

    @JsonProperty
    private double totalPrice;
}
