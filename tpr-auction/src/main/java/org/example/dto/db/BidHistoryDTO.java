package org.example.dto.db;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Entity(name = "BID_HISTORY")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BidHistoryDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BID_HISTORY_SEQ_ID")
    @SequenceGenerator(name = "BID_HISTORY_SEQ_ID", sequenceName = "BID_HISTORY_SEQ_ID", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "AUCTION_ID", referencedColumnName = "ID")
    private AuctionDTO auction;

    @ManyToOne
    @JoinColumn(name = "BID_ID", referencedColumnName = "ID")
    private BidDTO bid;

    @Column(name = "TIME")
    private Timestamp time;
}
