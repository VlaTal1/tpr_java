package org.example.bom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Model {

    private Long id;

    private String name;

    private Manufacturer manufacturer;
}
