package com.rhb.assessment.service;

import com.rhb.assessment.entity.Employee;
import com.rhb.assessment.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        // Clear the repository before each test to ensure a clean state
        employeeRepository.deleteAll();
    }

    @Test
    public void testGetEmployeesByDepartment() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Employee> employees = new PageImpl<>(List.of(new Employee()));

        when(employeeRepository.findByDepartmentName("IT", pageable)).thenReturn(employees);

        Page<Employee> result = employeeService.getEmployeesByDepartment("IT", 0, 10);

        assertEquals(1, result.getContent().size());
        verify(employeeRepository, times(1)).findByDepartmentName("IT", pageable);
    }

    @Test
    public void testCreateEmployee() {
        Employee employee = new Employee();
        employee.setName("John");
        employee.setRole("Developer");

        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee createdEmployee = employeeService.createEmployee(employee);

        assertNotNull(createdEmployee);
        assertEquals("John", createdEmployee.getName());
    }
}
