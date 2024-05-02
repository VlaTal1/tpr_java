package org.example.dto.db;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity(name = "COUNTRY")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COUNTRY_SEQ_ID")
    @SequenceGenerator(name = "COUNTRY_SEQ_ID", sequenceName = "COUNTRY_SEQ_ID", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    private String name;
}
