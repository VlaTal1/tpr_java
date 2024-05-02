package org.example.bom;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {

    private Long id;

    private String name;

    private String phone;

    private String position;
}
