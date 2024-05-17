package org.example.converter;

import org.example.bom.Employee;
import org.example.dto.db.EmployeeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EmployeeConverterTest {

    private Converter<EmployeeDTO, Employee> employeeConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeConverter = new EmployeeConverter();
    }

    @Test
    void fromDTO() {
        EmployeeDTO employeeDTO = new EmployeeDTO(1L, "Alex", "+38097847567", "Sales manager");

        Employee actualEmployee = employeeConverter.fromDTO(employeeDTO);

        assertNotNull(actualEmployee);
        assertEquals("Alex", actualEmployee.getName());
        assertEquals("+38097847567", actualEmployee.getPhone());
        assertEquals("Sales manager", actualEmployee.getPosition());
    }

    @Test
    void toDTO() {
        Employee employee = new Employee(1L, "Alex", "+38097847567", "Sales manager");

        EmployeeDTO actualEmployee = employeeConverter.toDTO(employee);

        assertNotNull(actualEmployee);
        assertEquals("Alex", actualEmployee.getName());
        assertEquals("+38097847567", actualEmployee.getPhone());
        assertEquals("Sales manager", actualEmployee.getPosition());
    }
}