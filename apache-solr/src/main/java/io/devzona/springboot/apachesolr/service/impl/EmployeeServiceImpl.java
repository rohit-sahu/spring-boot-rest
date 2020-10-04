package io.devzona.springboot.apachesolr.service.impl;

import io.devzona.springboot.apachesolr.exception.RecordNotFoundException;
import io.devzona.springboot.apachesolr.model.Employee;
import io.devzona.springboot.apachesolr.repository.EmployeeRepository;
import io.devzona.springboot.apachesolr.service.EmployeeService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
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
    @SneakyThrows
    @Cacheable(value = "employee", key = "#id", sync = true)
    public Employee getEmployeeById(Long id) throws RecordNotFoundException {
        Optional<Employee> employee = repository.findById(id);

        if (employee.isPresent()) {
            Thread.sleep(6000); // To Test the caching.
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
    @CacheEvict(value = "employee", key = "#id")
    public void deleteEmployeeById(Long id) throws RecordNotFoundException {
        Optional<Employee> employee = repository.findById(id);

        if (employee.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }
}
