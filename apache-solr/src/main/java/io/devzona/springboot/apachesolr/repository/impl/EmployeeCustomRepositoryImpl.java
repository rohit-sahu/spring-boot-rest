package io.devzona.springboot.apachesolr.repository.impl;

import io.devzona.springboot.apachesolr.model.Employee;
import io.devzona.springboot.apachesolr.repository.EmployeeCustomRepository;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeCustomRepositoryImpl extends GenericRepositoryImpl<Employee, Long> implements EmployeeCustomRepository {
}
