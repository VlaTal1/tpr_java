package org.example.dto.db;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity(name = "EMPLOYEE")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMPLOYEE_SEQ_ID")
    @SequenceGenerator(name = "EMPLOYEE_SEQ_ID", sequenceName = "EMPLOYEE_SEQ_ID", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "POSITION")
    private String position;
}
