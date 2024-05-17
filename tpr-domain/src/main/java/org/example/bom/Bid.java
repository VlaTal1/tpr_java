package org.example.bom;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bid {

    private Long id;

    private Client client;

    private Float amount;
}
