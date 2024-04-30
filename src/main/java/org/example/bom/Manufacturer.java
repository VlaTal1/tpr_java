package org.example.bom;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Manufacturer {

    private Long id;

    private String name;

    private Country country;
}
