package io.devzona.springboot.emailconsumer.service;

import io.devzona.springboot.emailconsumer.exception.RecordNotFoundException;
import io.devzona.springboot.emailconsumer.model.Employee;

import java.util.List;

public interface EmployeeService {

    Employee createOrUpdateEmployee(Employee entity) throws RecordNotFoundException;

    List<Employee> createOrUpdateEmployee(List<Employee> entities) throws RecordNotFoundException;

    void deleteEmployeeById(Long id) throws RecordNotFoundException;
}
