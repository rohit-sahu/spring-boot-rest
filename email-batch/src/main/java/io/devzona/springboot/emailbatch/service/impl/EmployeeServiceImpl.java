package io.devzona.springboot.emailbatch.service.impl;

import io.devzona.springboot.emailbatch.entity.Employee;
import io.devzona.springboot.emailbatch.exception.RecordNotFoundException;
import io.devzona.springboot.emailbatch.repository.EmployeeRepository;
import io.devzona.springboot.emailbatch.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository repository;

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = repository.findAll();
        if (employeeList.size() > 0) {
            return employeeList;
        } else {
            return new ArrayList<Employee>();
        }
    }

    @Override
    public Employee getEmployeeById(Long id) throws RecordNotFoundException {
        Optional<Employee> employee = repository.findById(id);

        if (employee.isPresent()) {
            return employee.get();
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }

    @Override
    public Employee createOrUpdateEmployee(Employee entity) throws RecordNotFoundException {
        Optional<Employee> employee = repository.findById(entity.getId());
        if (employee.isPresent()) {
            Employee newEntity = employee.get();
            newEntity.setEmail(entity.getEmail());
            newEntity.setFirstName(entity.getFirstName());
            newEntity.setLastName(entity.getLastName());
            newEntity = repository.save(newEntity);
            return newEntity;
        } else {
            entity = repository.save(entity);
            return entity;
        }
    }

    @Override
    public List<Employee> createOrUpdateEmployee(List<Employee> entities) throws RecordNotFoundException {
        return repository.saveAll(entities);
    }

    @Override
    public void deleteEmployeeById(Long id) throws RecordNotFoundException {
        Optional<Employee> employee = repository.findById(id);

        if (employee.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }
}
