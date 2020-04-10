package io.devzona.springboot.emailbatch.repository;

import io.devzona.springboot.emailbatch.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}