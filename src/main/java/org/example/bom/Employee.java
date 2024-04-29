package org.example.bom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Employee {

    private Long id;

    private String name;

    private String phone;

    private String position;
}
