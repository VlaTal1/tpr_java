package org.example.repository;

import org.example.dto.db.CountryDTO;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<CountryDTO, Long> {

}
