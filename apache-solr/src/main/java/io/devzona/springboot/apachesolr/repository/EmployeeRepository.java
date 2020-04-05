package io.devzona.springboot.apachesolr.repository;

import io.devzona.springboot.apachesolr.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
