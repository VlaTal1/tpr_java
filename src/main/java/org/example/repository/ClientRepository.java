package org.example.repository;

import org.example.bom.Client;
import org.example.dto.ClientDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientDTO, Long> {

}
