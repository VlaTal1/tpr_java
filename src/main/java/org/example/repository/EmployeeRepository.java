package org.example.repository;

import org.example.dto.db.EmployeeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeDTO, Long> {

}
