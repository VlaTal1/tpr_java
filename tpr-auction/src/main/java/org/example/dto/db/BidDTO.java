package org.example.dto.db;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity(name = "BID")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BidDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BID_SEQ_ID")
    @SequenceGenerator(name = "BID_SEQ_ID", sequenceName = "BID_SEQ_ID", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "ID")
    private ClientDTO client;

    @Column(name = "AMOUNT")
    private Float amount;
}
