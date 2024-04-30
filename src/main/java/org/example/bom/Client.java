package org.example.bom;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {

    private Long id;

    private String name;

    private String address;

    private String phone;

    private String passport;

    private int discount;
}
