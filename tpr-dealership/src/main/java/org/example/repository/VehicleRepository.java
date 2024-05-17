package org.example.repository;

import org.example.dto.db.VehicleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleDTO, Long> {

}
