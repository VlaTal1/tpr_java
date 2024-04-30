package org.example.repository;

import org.example.dto.db.ColorDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<ColorDTO, Long> {

}
