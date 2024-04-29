package org.example.dto;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity(name = "CLIENT")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLIENT_SEQ_ID")
    @SequenceGenerator(name = "CLIENT_SEQ_ID", sequenceName = "CLIENT_SEQ_ID", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "PASSPORT")
    private String passport;

    @Column(name = "DISCOUNT")
    private Integer discount;
}
