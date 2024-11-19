package com.rhb.assessment.service;

import com.rhb.assessment.entity.Department;
import com.rhb.assessment.entity.Employee;
import com.rhb.assessment.repository.DepartmentRepository;
import com.rhb.assessment.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private RestTemplate restTemplate;

    // Get employees by department with pagination
    public Page<Employee> getEmployeesByDepartment(String departmentName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findByDepartmentName(departmentName, pageable);
    }

    // Add new employee
    public Employee createEmployee(Employee employee) {
        // Check if the department already exists by ID or Name (you can choose the logic)
        Department department = employee.getDepartment();
        if (department != null && department.getId() == null) {
            // If the department does not have an ID, create a new department
            department = departmentRepository.save(department);
        } else if (department != null) {
            // If the department has an ID, fetch the existing department
            department = departmentRepository.findById(department.getId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
        }
        employee.setDepartment(department); // Associate the employee with the department
        return employeeRepository.save(employee);
    }

    // Update employee details
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setName(updatedEmployee.getName());
                    employee.setRole(updatedEmployee.getRole());
                    employee.setDepartment(updatedEmployee.getDepartment());
                    return employeeRepository.save(employee);
                }).orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    // Delete an employee
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    // Call external API (e.g., Google)
    public String callExternalAPI() {
        String url = "https://www.google.com";
        return restTemplate.getForObject(url, String.class);
    }
}
