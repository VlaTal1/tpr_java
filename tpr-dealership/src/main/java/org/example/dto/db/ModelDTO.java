package org.example.dto.db;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity(name = "MODEL")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MODEL_SEQ_ID")
    @SequenceGenerator(name = "MODEL_SEQ_ID", sequenceName = "MODEL_SEQ_ID", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "MANUFACTURER_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_MODEL_MANUFACTURER"))
    private ManufacturerDTO manufacturer;
}
