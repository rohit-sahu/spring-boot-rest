package com.springbootrest.h2database.service;

import com.springbootrest.h2database.exception.RecordNotFoundException;
import com.springbootrest.h2database.model.Employee;

import java.util.List;

public interface EmployeeService {

    public List<Employee> getAllEmployees();

    public Employee getEmployeeById(Long id) throws RecordNotFoundException;

    public Employee createOrUpdateEmployee(Employee entity) throws RecordNotFoundException;

    public void deleteEmployeeById(Long id) throws RecordNotFoundException;
}
