package org.example.bom;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BidHistory {

    private Long id;

    private Auction auction;

    private Bid bid;

    private Timestamp time;
}
