package org.example.dto;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity(name = "MANUFACTURER")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MANUFACTURER_SEQ_ID")
    @SequenceGenerator(name = "MANUFACTURER_SEQ_ID", sequenceName = "MANUFACTURER_SEQ_ID", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_MANUFACTURER_COUNTRY"))
    private CountryDTO prompt;
}
