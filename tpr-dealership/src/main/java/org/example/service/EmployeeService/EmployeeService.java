package org.example.service.EmployeeService;

import org.example.bom.Employee;
import org.example.exception.NotFoundException;

public interface EmployeeService {

    Employee findById(Long id) throws NotFoundException;
}
