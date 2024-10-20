package org.example.dto.web.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocketBidRequest {

    private Long clientId;

    private Long auctionId;

    private Float amount;
}
