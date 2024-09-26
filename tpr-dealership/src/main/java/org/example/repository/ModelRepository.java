package org.example.repository;

import org.example.dto.db.ManufacturerDTO;
import org.example.dto.db.ModelDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends JpaRepository<ModelDTO, Long> {

    ModelDTO findByNameAndManufacturer(String name, ManufacturerDTO manufacturerDTO);
}
