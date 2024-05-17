package org.example.repository;

import org.example.dto.db.ClientDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientDTO, Long> {

    ClientDTO findByPhone(String phone);

    ClientDTO findByNameAndAddressAndPhoneAndPassport(String name, String address, String phone, String passport);
}
