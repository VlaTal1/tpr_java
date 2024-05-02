package org.example.repository;

import org.example.dto.db.ManufacturerDTO;
import org.springframework.data.repository.CrudRepository;

public interface ManufacturerRepository extends CrudRepository<ManufacturerDTO, Long> {

}
