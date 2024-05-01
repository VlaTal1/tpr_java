package org.example.bom;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {

    @JsonProperty
    private Long id;

    private String name;

    private String address;

    private String phone;

    private String passport;

    private int discount;
}
