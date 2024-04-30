package org.example.service.EmployeeService;

import lombok.RequiredArgsConstructor;
import org.example.exception.NotFoundException;
import org.example.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public void checkById(Long id) throws NotFoundException {
        employeeRepository.findById(id).orElseThrow(() -> new NotFoundException(STR."Employee with id \{id} not found"));
    }
}
