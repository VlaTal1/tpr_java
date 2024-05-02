package org.example.dto.web;

import lombok.*;
import org.example.bom.PaymentType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DealRequest {

    private Long vehicleId;

    private Long clientId;

    private Long employeeId;

    private PaymentType paymentType;
}
