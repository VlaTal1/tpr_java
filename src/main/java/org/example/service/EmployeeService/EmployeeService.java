package org.example.service.EmployeeService;

import org.example.exception.NotFoundException;

public interface EmployeeService {

    void checkById(Long id) throws NotFoundException;
}
