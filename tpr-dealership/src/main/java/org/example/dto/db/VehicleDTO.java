package org.example.dto.db;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity(name = "VEHICLE")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VEHICLE_SEQ_ID")
    @SequenceGenerator(name = "VEHICLE_SEQ_ID", sequenceName = "VEHICLE_SEQ_ID", allocationSize = 1)
    private Long id;

    @Column(name = "AMOUNT")
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "MODEL_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_VEHICLE_MODEL"))
    private ModelDTO model;

    @Column(name = "TYPE")
    private String type;

    @ManyToOne
    @JoinColumn(name = "COLOR_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_VEHICLE_COLOR"))
    private ColorDTO color;

    @Column(name = "PRICE")
    private Float price;

    @Column(name = "YEAR")
    private Integer year;
}
