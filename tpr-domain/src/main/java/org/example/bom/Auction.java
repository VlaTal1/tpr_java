package org.example.bom;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Auction {

    private Long id;

    private String name;

    private Long vehicleId;

    private Timestamp startTime;

    private Integer bidTimeoutSec;

    private Float startPrice;

    private Float minBid;

    private AuctionStatus auctionStatus;
}
