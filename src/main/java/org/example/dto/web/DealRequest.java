package org.example.dto.web;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DealRequest {

    private Long vehicleId;

    private Long clientId;

    private Long employeeId;
}
