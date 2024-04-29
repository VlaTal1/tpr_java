package org.example.dto;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity(name = "VEHICLE_TYPE")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTypeDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VEHICLE_TYPE_SEQ_ID")
    @SequenceGenerator(name = "VEHICLE_TYPE_SEQ_ID", sequenceName = "VEHICLE_TYPE_SEQ_ID", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    private String name;
}
