package org.example.service.EmployeeService;

import lombok.RequiredArgsConstructor;
import org.example.bom.Employee;
import org.example.converter.Converter;
import org.example.dto.db.EmployeeDTO;
import org.example.exception.NotFoundException;
import org.example.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final Converter<EmployeeDTO, Employee> employeeConverter;

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee findById(Long id) throws NotFoundException {
        EmployeeDTO employeeDTO = employeeRepository.findById(id).orElseThrow(() -> new NotFoundException(STR."Employee with id \{id} not found"));
        return employeeConverter.fromDTO(employeeDTO);
    }
}
