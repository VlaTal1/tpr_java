package org.example.repository;

import org.example.dto.db.BidDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends JpaRepository<BidDTO, Long> {

}
