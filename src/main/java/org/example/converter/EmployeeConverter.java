package org.example.converter;

import org.example.bom.Employee;
import org.example.dto.db.EmployeeDTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeeConverter implements Converter<EmployeeDTO, Employee> {

    @Override
    public Employee fromDTO(EmployeeDTO DTO) {
        return Employee.builder()
                .id(DTO.getId())
                .name(DTO.getName())
                .phone(DTO.getPhone())
                .position(DTO.getPosition())
                .build();
    }

    @Override
    public EmployeeDTO toDTO(Employee BOM) {
        return EmployeeDTO.builder()
                .id(BOM.getId())
                .name(BOM.getName())
                .phone(BOM.getPhone())
                .position(BOM.getPosition())
                .build();
    }
}
