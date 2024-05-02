package org.example.bom;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Manufacturer {

    @JsonProperty(required = false)
    private Long id;

    private String name;

    private Country country;
}
