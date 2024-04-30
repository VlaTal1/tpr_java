package org.example.dto.db;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Entity(name = "DEAL")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEAL_SEQ_ID")
    @SequenceGenerator(name = "DEAL_SEQ_ID", sequenceName = "DEAL_SEQ_ID", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "VEHICLE_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_DEAL_VEHICLE"))
    private VehicleDTO vehicle;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_DEAL_CLIENT"))
    private ClientDTO client;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_DEAL_EMPLOYEE"))
    private EmployeeDTO employee;

    @Column(name = "DATE")
    private Timestamp date;

    @Column(name = "TOTAL_PRICE")
    private Float totalPrice;
}
