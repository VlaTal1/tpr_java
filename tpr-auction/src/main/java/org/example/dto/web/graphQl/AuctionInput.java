package org.example.dto.web.graphQl;

public record AuctionInput(
    String name,
    Long vehicleId,
    String startTime,
    Integer bidTimeoutSec,
    Float startPrice,
    Float minBid
) {}
