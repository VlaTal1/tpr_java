package org.example.bom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Client {

    private Long id;

    private String name;

    private String address;

    private String phone;

    private String passport;

    private int discount;
}
