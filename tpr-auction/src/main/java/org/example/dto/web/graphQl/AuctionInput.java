package org.example.dto.web.graphQl;

import java.sql.Timestamp;

public record AuctionInput(
    String name,
    Long vehicleId,
    Timestamp startTime,
    Integer bidTimeoutSec,
    Float startPrice,
    Float minBid
) {}
