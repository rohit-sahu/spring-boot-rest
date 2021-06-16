package io.devzona.springboot.apachekafkaproducer.service.impl;

import io.devzona.springboot.apachekafkaproducer.exception.RecordNotFoundException;
import io.devzona.springboot.apachekafkaproducer.model.Employee;
import io.devzona.springboot.apachekafkaproducer.service.EmployeeService;
import io.devzona.springboot.apachekafkaproducer.service.KafkaProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    KafkaProducerService kafkaProducerService;

    @Override
    public Employee createOrUpdateEmployee(Employee entity) throws RecordNotFoundException {
        kafkaProducerService.send("CREATE_EMPLOYEE", entity);
        return entity;
    }

    @Override
    public List<Employee> createOrUpdateEmployee(List<Employee> entities) throws RecordNotFoundException {
        kafkaProducerService.send("CREATE_EMPLOYEES", entities);
        return entities;
    }

    @Override
    @CacheEvict(value = "employee", key = "#id")
    public void deleteEmployeeById(Long id) throws RecordNotFoundException {
        kafkaProducerService.send("DELETE_EMPLOYEE", id);
        if (ObjectUtils.isEmpty(id)) {
            return;
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }
}
