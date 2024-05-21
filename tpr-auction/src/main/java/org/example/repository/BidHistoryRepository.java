package org.example.repository;

import org.example.dto.db.AuctionDTO;
import org.example.dto.db.BidHistoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidHistoryRepository extends JpaRepository<BidHistoryDTO, Long> {

    BidHistoryDTO findFirstByAuctionOrderByTimeDesc(AuctionDTO auctionDTO);

    List<BidHistoryDTO> findAllByAuction(AuctionDTO auctionDTO);

    @Query("SELECT bh FROM BID_HISTORY bh " +
            "JOIN bh.auction a " +
            "JOIN bh.bid b " +
            "WHERE a.auctionStatus = 'ENDED' " +
            "AND EXTRACT(YEAR FROM bh.time) = :year " +
            "AND b.amount = (SELECT MAX(b2.amount) FROM BID_HISTORY bh2 " +
            "JOIN bh2.bid b2 " +
            "WHERE bh2.auction = bh.auction " +
            "AND EXTRACT(YEAR FROM bh2.time) = :year) " +
            "ORDER BY bh.auction.id")
    List<BidHistoryDTO> findMaxBidAmountForEndedAuctionsByYear(@Param("year") Integer year);
}
