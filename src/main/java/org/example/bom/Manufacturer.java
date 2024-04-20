package org.example.bom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Manufacturer {

    private Long id;

    private String name;

    private Country country;
}
