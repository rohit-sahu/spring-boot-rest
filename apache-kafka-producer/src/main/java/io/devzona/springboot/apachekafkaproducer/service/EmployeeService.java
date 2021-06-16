package io.devzona.springboot.apachekafkaproducer.service;

import io.devzona.springboot.apachekafkaproducer.exception.RecordNotFoundException;
import io.devzona.springboot.apachekafkaproducer.model.Employee;

import java.util.List;

public interface EmployeeService {

    Employee createOrUpdateEmployee(Employee entity) throws RecordNotFoundException;

    List<Employee> createOrUpdateEmployee(List<Employee> entities) throws RecordNotFoundException;

    void deleteEmployeeById(Long id) throws RecordNotFoundException;
}
