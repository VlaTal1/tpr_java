package org.example.repository;

import org.example.dto.db.DealDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealRepository extends JpaRepository<DealDTO, Long> {

}
