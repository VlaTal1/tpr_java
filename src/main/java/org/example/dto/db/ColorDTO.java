package org.example.dto.db;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity(name = "COLOR")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColorDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COLOR_SEQ_ID")
    @SequenceGenerator(name = "COLOR_SEQ_ID", sequenceName = "COLOR_SEQ_ID", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    private String name;
}
