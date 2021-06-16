package io.devzona.springboot.emailconsumer.service.impl;

import io.devzona.springboot.emailconsumer.exception.RecordNotFoundException;
import io.devzona.springboot.emailconsumer.model.Employee;
import io.devzona.springboot.emailconsumer.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public Employee createOrUpdateEmployee(Employee entity) throws RecordNotFoundException {
        return entity;
    }

    @Override
    public List<Employee> createOrUpdateEmployee(List<Employee> entities) throws RecordNotFoundException {
        return entities;
    }

    @Override
    @CacheEvict(value = "employee", key = "#id")
    public void deleteEmployeeById(Long id) throws RecordNotFoundException {
        if (ObjectUtils.isEmpty(id)) {
            return;
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }
}
