package io.devzona.springboot.emailbatch.service;

import io.devzona.springboot.emailbatch.entity.Employee;
import io.devzona.springboot.emailbatch.exception.RecordNotFoundException;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id) throws RecordNotFoundException;

    Employee createOrUpdateEmployee(Employee entity) throws RecordNotFoundException;

    List<Employee> createOrUpdateEmployee(List<Employee> entities) throws RecordNotFoundException;

    void deleteEmployeeById(Long id) throws RecordNotFoundException;
}
