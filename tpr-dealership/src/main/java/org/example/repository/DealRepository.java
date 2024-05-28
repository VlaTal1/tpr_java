package org.example.repository;

import org.example.dto.db.DealDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DealRepository extends JpaRepository<DealDTO, Long> {

    @Query("SELECT d FROM DEAL d WHERE EXTRACT(YEAR FROM d.date) = :year")
    List<DealDTO> findAllDealsByYear(@Param("year") Integer year);
}
