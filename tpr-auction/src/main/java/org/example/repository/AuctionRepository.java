package org.example.repository;

import org.example.dto.db.AuctionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<AuctionDTO, Long> {

    List<AuctionDTO> findByNameContaining(String name);
}
