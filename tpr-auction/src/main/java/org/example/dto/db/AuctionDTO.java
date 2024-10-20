package org.example.dto.db;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Entity(name = "AUCTION")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUCTION_SEQ_ID")
    @SequenceGenerator(name = "AUCTION_SEQ_ID", sequenceName = "AUCTION_SEQ_ID", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "VEHICLE_ID")
    private Long vehicleId;

    @Column(name = "START_TIME")
    private Timestamp startTime;

    @Column(name = "BID_TIMEOUT_SEC")
    private Integer bidTimeoutSec;

    @Column(name = "START_PRICE")
    private Float startPrice;

    @Column(name = "MIN_BID")
    private Float minBid;

    @Column(name = "AUCTION_STATUS")
    private String auctionStatus;
}
