package io.devzona.springboot.apachesolr.service;

import io.devzona.springboot.apachesolr.exception.RecordNotFoundException;
import io.devzona.springboot.apachesolr.model.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id) throws RecordNotFoundException;

    Employee createOrUpdateEmployee(Employee entity) throws RecordNotFoundException;

    List<Employee> createOrUpdateEmployee(List<Employee> entities) throws RecordNotFoundException;

    void deleteEmployeeById(Long id) throws RecordNotFoundException;
}
