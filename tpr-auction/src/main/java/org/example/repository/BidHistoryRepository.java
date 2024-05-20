package org.example.repository;

import org.example.dto.db.AuctionDTO;
import org.example.dto.db.BidHistoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidHistoryRepository extends JpaRepository<BidHistoryDTO, Long> {

    BidHistoryDTO findFirstByAuctionOrderByTimeDesc(AuctionDTO auctionDTO);

    List<BidHistoryDTO> findAllByAuction(AuctionDTO auctionDTO);
}
