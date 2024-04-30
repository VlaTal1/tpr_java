package org.example.bom;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Model {

    private Long id;

    private String name;

    private Manufacturer manufacturer;
}
