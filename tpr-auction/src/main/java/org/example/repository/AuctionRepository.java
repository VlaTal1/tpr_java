package org.example.repository;

import org.example.dto.db.AuctionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRepository extends JpaRepository<AuctionDTO, Long> {

}
