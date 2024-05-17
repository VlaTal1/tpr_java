package org.example.repository;

import org.example.dto.db.BidHistoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidHistoryRepository extends JpaRepository<BidHistoryDTO, Long> {

}
