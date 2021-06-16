package io.devzona.springboot.emailproducer.service;

import io.devzona.springboot.emailproducer.exception.RecordNotFoundException;
import io.devzona.springboot.emailproducer.model.Employee;

import java.util.List;

public interface EmployeeService {

    Employee createOrUpdateEmployee(Employee entity) throws RecordNotFoundException;

    List<Employee> createOrUpdateEmployee(List<Employee> entities) throws RecordNotFoundException;

    void deleteEmployeeById(Long id) throws RecordNotFoundException;
}
