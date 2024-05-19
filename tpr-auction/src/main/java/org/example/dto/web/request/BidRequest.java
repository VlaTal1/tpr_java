package org.example.dto.web.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BidRequest {

    private Long id;

    private Long clientId;

    private Float amount;
}
