package org.example.service;

import org.example.bom.Employee;
import org.example.converter.Converter;
import org.example.dto.db.EmployeeDTO;
import org.example.exception.NotFoundException;
import org.example.repository.EmployeeRepository;
import org.example.service.EmployeeService.EmployeeService;
import org.example.service.EmployeeService.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmployeeServiceTest {

    @Mock
    private Converter<EmployeeDTO, Employee> employeeConverter;

    @Mock
    private EmployeeRepository employeeRepository;

    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeService = new EmployeeServiceImpl(employeeConverter, employeeRepository);
    }

    @Test
    void findById() throws NotFoundException {
        Employee employee = new Employee(1L, "Alex", "+38097847567", "Sales manages");
        EmployeeDTO employeeDTO = new EmployeeDTO(1L, "Alex", "+38097847567", "Sales manages");

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employeeDTO));
        when(employeeConverter.fromDTO(employeeDTO)).thenReturn(employee);

        Employee actualEmployee = employeeService.findById(employee.getId());

        assertEquals(employee, actualEmployee);

        verify(employeeRepository).findById(employee.getId());
        verify(employeeConverter).fromDTO(employeeDTO);
    }

    @Test
    void findById_NotFound() {
        Employee employee = new Employee(1L, "Alex", "+38097847567", "Sales manages");

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> employeeService.findById(employee.getId()));

        verify(employeeRepository).findById(employee.getId());
    }
}